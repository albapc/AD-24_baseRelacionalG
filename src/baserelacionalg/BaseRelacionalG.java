package baserelacionalg;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author Alba
 */
public class BaseRelacionalG {

    public static Connection conn = null;

    private Connection conexion() {
        final String driver = "jdbc:oracle:thin:";
        final String host = "localhost.localdomain";
        final String porto = "1521";
        final String sid = "orcl";
        final String usuario = "hr";
        final String password = "hr";
        String url = driver + usuario + "/" + password + "@" + host + ":" + porto + ":" + sid;

//        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void callStatementFunction(String codigo) {
        try (Connection conn = this.conexion()) {

            //Preparamos el callable statement y llamamos a la funcion alojada en la bd
            CallableStatement cstmt = conn.prepareCall("{? = call prezo_produto(?)}");
            //se almacena el resultado de la funcion en una variable del mismo tipo que retorne (de ahi el '?' del inicio)

            //Especificamos el/los parámetro(s) de entrada
            cstmt.setString(2, codigo);

            //Registramos los parámetros de salida (RETURN)
            cstmt.registerOutParameter(1, Types.INTEGER);

            //Ejecutamos CallableStatement, y recibimos cualquier conjunto de resultados o parámetros de salida
            cstmt.execute();

            //Almacenamos el resultado del procedure en una variable y la imprimimos
            int result = cstmt.getInt(1);

            System.out.println("Produto " + codigo + "\nPrezo: " + result);

//            cstmt.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void main(String[] args) throws SQLException {
        BaseRelacionalG obx = new BaseRelacionalG();
        obx.callStatementFunction("p3");
        conn.close();

    }

}
