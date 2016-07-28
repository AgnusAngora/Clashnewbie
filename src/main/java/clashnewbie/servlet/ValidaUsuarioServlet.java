package clashnewbie.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class validaUsuario
 */
@WebServlet(name = "ValidaUsuarioServlet", urlPatterns = {"/ValidaUsuarioServlet", "/validaUsuario"})
public class ValidaUsuarioServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        UsuarioNegocio objUsuarioNegocio = new UsuarioNegocio();
        String login = request.getParameter("txtLogin");
        String pass = request.getParameter("txtPass");

        Usuario objUsuario = objUsuarioNegocio.compruebaUsuario(login, pass);
        if (objUsuario != null) {
            sesion.setAttribute("usuarioConectado", objUsuario);
            response.sendRedirect("clashnewbie.html");
        } else {
            response.sendRedirect("index.html");
        }

    }

}
