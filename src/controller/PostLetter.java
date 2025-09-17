package controller;

import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.LetterDao;
import model.LetterDto;
import model.UserDto;

@MultipartConfig
public class PostLetter extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("LOGIN_INFO");

        if (loginUser != null) {
            String recipientIdStr = request.getParameter("RECIPIENT_ID");
            if (recipientIdStr == null || recipientIdStr.isEmpty()) {
                response.sendRedirect("LetterMain");
                return;
            }

            int recipientId = Integer.parseInt(recipientIdStr);
            String message = request.getParameter("MESSAGE");
            Part filePart = request.getPart("IMAGE");
            String imageFilename = null;

            if (filePart != null && filePart.getSize() > 0) {
                String submittedFilename = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                // ユニークなファイル名を生成（タイムスタンプ + 元のファイル名）
                imageFilename = System.currentTimeMillis() + "_" + submittedFilename;
                
                // 保存先の絶対パスを取得
                String uploadPath = getServletContext().getRealPath("/uploads");
                java.io.File uploadDir = new java.io.File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();

                filePart.write(uploadPath + java.io.File.separator + imageFilename);
            }

            LetterDto dto = new LetterDto();
            dto.setSenderId(loginUser.getUserId());
            dto.setRecipientId(recipientId);
            dto.setMessage(message);
            dto.setImageFilename(imageFilename);

            LetterDao dao = new LetterDao();
            dao.doInsert(dto);

            response.sendRedirect("LetterMain");

        } else {
            response.sendRedirect("Login");
        }
    }
}
