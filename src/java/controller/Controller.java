package controller;

import bean.Aluno;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Model;

public class Controller extends HttpServlet {

    //variveis para o ambiente 
    String nome;
    String curso;
    Aluno aluno = new Aluno();
    List<Aluno> alunosDados;
    List<Aluno> alunoDados;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
    }

    // <editor-fold defaultstate="collapsed" 
    // desc="HttpServlet methods. 
    // Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // configuração para manter a acentuação e 
        // caracteres especiais
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // conexão com a camada do Model (Banco de Dados)
        try {
            // chamar o Model
            Model alunoModel = new Model();

            //atribuindo os valores do Model para ua variável
            alunosDados = alunoModel.listar();

            request.setAttribute("listaAlunos", alunosDados);
            request.getRequestDispatcher("view_listar.jsp").
                    forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("mensagem", e.getMessage());
            request.getRequestDispatcher("view_mensagem.jsp").
                    forward(request, response);
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        // essas linhas configuram o código de página 
        // ou seja, acentos e caracteres especiais
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Opçãpo que vem do formulário
        // <input type="hidden" 
        //        name="operacao" value="tipo_da_operacao" />
        String operacao = request.getParameter("operacao");

        // seleciona a opção armazena em "operacao"
        switch (operacao) {
            case "Inserir":
               try {
                    // passar os valores recebeidos do formulário para o objeto
                    aluno.setRa(request.getParameter("ra"));
                    aluno.setNome(request.getParameter("nome"));
                    aluno.setCurso(request.getParameter("curso"));

                    // chama o model para fazer a operação
                    Model alunoModel = new Model();

                    // chamar o método de inclusão, passando o objeto "aluno"
                    alunoModel.inserir(aluno);

                    // composição da mensagem de retorno
                    request.setAttribute("mensagem", alunoModel.toString());

                } catch (SQLException ex) {
                    // composição da mensagem de retorno
                    request.setAttribute("mensagem", ex.getMessage());
                }
                // redireciona para a view_mensagem
                request.getRequestDispatcher("view_mensagem.jsp").
                        forward(request, response);
                break;

            case "Pesquisar":
                request.setAttribute("mensagem",
                        "Você clicou em Pesquisar");
                break;

            case "Editar":
                request.setAttribute("mensagem",
                        "Você clicou em Editar");
                break;

            case "Atualizar":
                request.setAttribute("mensagem",
                        "Você clicou em Atualizar");
                break;

            case "Excluir":
              try {
                    // passar os valores recebeidos do formulário para o objeto
                    aluno.setRa(request.getParameter("ra"));
                   
                    // chama o model para fazer a operação
                    Model alunoModel = new Model();

                    // chamar o método de inclusão, passando o objeto "aluno"
                    alunoModel.delete(aluno);

                    // composição da mensagem de retorno
                    request.setAttribute("mensagem", alunoModel.toString());

                } catch (SQLException ex) {
                    // composição da mensagem de retorno
                    request.setAttribute("mensagem", ex.getMessage());
                }
                // redireciona para a view_mensagem
                request.getRequestDispatcher("view_mensagem.jsp").
                        forward(request, response);
                break;

            case "Confirmar Exclusao":
                request.setAttribute("mensagem",
                        "Você clicou em Confirmar Exclusão");
                break;
        }
        // redireciona para a view_mensagem
        request.getRequestDispatcher("view_mensagem.jsp").
                forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
