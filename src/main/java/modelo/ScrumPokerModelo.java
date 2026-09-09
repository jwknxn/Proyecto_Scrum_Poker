package modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScrumPokerModelo {

    private final ConexionBDD conexion;

    public ScrumPokerModelo() {
        conexion = new ConexionBDD();
    }

    public void registrarParticipante(Participante participante) throws SQLException {
        if (participante == null) {
            throw new IllegalArgumentException("El participante es obligatorio.");
        }

        validarTexto(participante.getNombre(), "el nombre");
        validarTexto(participante.getApellido(), "el apellido");

        String rol = participante.obtenerRol();

        if (!rol.equals("Desarrollador") && !rol.equals("Product Owner")) {
            throw new IllegalArgumentException("Seleccione un rol válido.");
        }

        ejecutar(
                "{CALL sp_registrar_participante(?,?,?)}",
                participante.getNombre().trim(),
                participante.getApellido().trim(),
                rol
        );
    }

    public List<Participante> consultarParticipantes() throws SQLException {
        List<Participante> lista = new ArrayList<>();

        try (Connection cn = conexion.conectar();
             CallableStatement cs = cn.prepareCall("{CALL sp_consultar_participantes()}");
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_participante");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String rol = rs.getString("rol");

                if (rol.equals("Product Owner")) {
                    lista.add(new ProductOwner(id, nombre, apellido));
                } else {
                    lista.add(new Desarrollador(id, nombre, apellido));
                }
            }
        }

        return lista;
    }

    public List<ProductOwner> consultarProductOwners() throws SQLException {
        List<ProductOwner> lista = new ArrayList<>();

        try (Connection cn = conexion.conectar();
             CallableStatement cs = cn.prepareCall("{CALL sp_consultar_product_owners()}");
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(new ProductOwner(
                        rs.getInt("id_product_owner"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                ));
            }
        }

        return lista;
    }

    public void registrarHistoria(HistoriaUsuario historia) throws SQLException {
        if (historia == null) {
            throw new IllegalArgumentException("La historia es obligatoria.");
        }

        validarTexto(historia.getTitulo(), "el título");
        validarTexto(historia.getDescripcion(), "la descripción");
        validarId(historia.getIdPO(), "el Product Owner");

        ejecutar(
                "{CALL sp_registrar_historia(?,?,?)}",
                historia.getTitulo().trim(),
                historia.getDescripcion().trim(),
                historia.getIdPO()
        );
    }

    public List<HistoriaUsuario> consultarHistorias() throws SQLException {
        List<HistoriaUsuario> lista = new ArrayList<>();

        try (Connection cn = conexion.conectar();
             CallableStatement cs = cn.prepareCall("{CALL sp_consultar_historias()}");
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(new HistoriaUsuario(
                        rs.getInt("id_historia"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getInt("id_product_owner")
                ));
            }
        }

        return lista;
    }

    public List<HistoriaUsuario> consultarHistoriasPO(int idPO) throws SQLException {
        validarId(idPO, "el ID del Product Owner");

        List<HistoriaUsuario> lista = new ArrayList<>();

        try (Connection cn = conexion.conectar();
             CallableStatement cs = cn.prepareCall("{CALL sp_consultar_historias_po(?)}")) {

            cs.setInt(1, idPO);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(new HistoriaUsuario(
                            rs.getInt("id_historia"),
                            rs.getString("titulo"),
                            rs.getString("descripcion"),
                            rs.getInt("id_product_owner")
                    ));
                }
            }
        }

        return lista;
    }

    public List<Desarrollador> consultarDesarrolladores() throws SQLException {
        List<Desarrollador> lista = new ArrayList<>();

        try (Connection cn = conexion.conectar();
             CallableStatement cs = cn.prepareCall("{CALL sp_consultar_desarrolladores()}");
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(new Desarrollador(
                        rs.getInt("id_desarrollador"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                ));
            }
        }

        return lista;
    }

    public List<PuntoEstimacion> consultarPuntos() throws SQLException {
        List<PuntoEstimacion> lista = new ArrayList<>();

        try (Connection cn = conexion.conectar();
             CallableStatement cs = cn.prepareCall("{CALL sp_consultar_puntos()}");
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(new PuntoEstimacion(
                        rs.getInt("id_punto"),
                        rs.getInt("valor")
                ));
            }
        }

        return lista;
    }

    public void registrarVoto(Voto voto) throws SQLException {
        if (voto == null) {
            throw new IllegalArgumentException("El voto es obligatorio.");
        }

        validarId(voto.getIdDesarrollador(), "el ID del desarrollador");
        validarId(voto.getIdHistoriaUsuario(), "el ID de la historia");
        validarId(voto.getIdPunto(), "el ID del punto");

        try (Connection cn = conexion.conectar();
             CallableStatement cs = cn.prepareCall("{CALL sp_registrar_voto(?,?,?)}")) {

            cs.setInt(1, voto.getIdDesarrollador());
            cs.setInt(2, voto.getIdHistoriaUsuario());
            cs.setInt(3, voto.getIdPunto());
            cs.executeUpdate();

        } catch (SQLException e) {
            if ("45000".equals(e.getSQLState())) {
                throw new IllegalArgumentException(e.getMessage());
            }

            if (e.getErrorCode() == 1062) {
                throw new IllegalArgumentException(
                        "El desarrollador ya votó esta Historia de Usuario."
                );
            }

            throw e;
        }
    }

    public List<VotoDetalle> consultarVotos(int idHistoria) throws SQLException {
        validarId(idHistoria, "el ID de la historia");

        List<VotoDetalle> lista = new ArrayList<>();

        try (Connection cn = conexion.conectar();
             CallableStatement cs = cn.prepareCall("{CALL sp_consultar_votos_historia(?)}")) {

            cs.setInt(1, idHistoria);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(new VotoDetalle(
                            rs.getInt("id_voto"),
                            rs.getInt("id_desarrollador"),
                            rs.getString("desarrollador"),
                            rs.getInt("id_historia"),
                            rs.getString("historia"),
                            rs.getInt("puntos")
                    ));
                }
            }
        }

        return lista;
    }

    public ResultadoEstimacion calcularEstimacion(int idHistoria) throws SQLException {
        validarId(idHistoria, "el ID de la historia");

        try (Connection cn = conexion.conectar();
             CallableStatement cs = cn.prepareCall("{CALL sp_calcular_estimacion(?)}")) {

            cs.setInt(1, idHistoria);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    String promedio = rs.getString("estimacion_promedio");

                    return new ResultadoEstimacion(
                            rs.getInt("id_historia"),
                            rs.getString("titulo"),
                            rs.getInt("cantidad_votos"),
                            promedio == null ? "Sin votos" : promedio
                    );
                }
            }
        }

        return null;
    }

    public List<HistoriaUsuario> consultarHistoriasDesarrollador(int idDesarrollador) throws SQLException {
        validarId(idDesarrollador, "el ID del desarrollador");

        List<HistoriaUsuario> lista = new ArrayList<>();

        try (Connection cn = conexion.conectar();
             CallableStatement cs = cn.prepareCall("{CALL sp_consultar_historias_desarrollador(?)}")) {

            cs.setInt(1, idDesarrollador);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(new HistoriaUsuario(
                            rs.getInt("id_historia"),
                            rs.getString("titulo"),
                            rs.getString("descripcion"),
                            rs.getInt("id_product_owner")
                    ));
                }
            }
        }

        return lista;
    }

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingrese " + campo + ".");
        }
    }

    private void validarId(int id, String campo) {
        if (id <= 0) {
            throw new IllegalArgumentException("Ingrese " + campo + " válido.");
        }
    }

    private void ejecutar(String procedimiento, Object... parametros) throws SQLException {
        try (Connection cn = conexion.conectar();
             CallableStatement cs = cn.prepareCall(procedimiento)) {

            for (int i = 0; i < parametros.length; i++) {
                cs.setObject(i + 1, parametros[i]);
            }

            cs.executeUpdate();
        }
    }
}
