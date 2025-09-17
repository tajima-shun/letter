package controller;

import java.io.File;
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
public class UpdateLetter extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("LOGIN_INFO");

        if (loginUser != null) {
            try {
                int letterId = Integer.parseInt(request.getParameter("LETTER_ID"));
                String message = request.getParameter("MESSAGE");
                boolean deleteImage = "true".equals(request.getParameter("DELETE_IMAGE"));
                Part newFilePart = request.getPart("NEW_IMAGE");

                LetterDao dao = new LetterDao();
                
                // ★★★ 修正点：まずDBから現在の情報を取得 ★★★
                LetterDto currentLetter = dao.selectByLetterId(letterId, loginUser.getUserId());
                if (currentLetter == null) {
                    // 他人の手紙を編集しようとした、または手紙が存在しない
                    response.sendRedirect("LetterMain");
                    return;
                }
                String currentImageFilename = currentLetter.getImageFilename();
                String newImageFilename = currentImageFilename; // デフォルトは現在の画像を維持

                String uploadPath = getServletContext().getRealPath("/uploads");

                // 1. 新しい画像がアップロードされたか？
                if (newFilePart != null && newFilePart.getSize() > 0) {
                    // 古い画像があれば物理的に削除
                    if (currentImageFilename != null && !currentImageFilename.isEmpty()) {
                        File oldFile = new File(uploadPath, currentImageFilename);
                        if (oldFile.exists()) {
                            oldFile.delete();
                        }
                    }
                    // 新しい画像を保存し、ファイル名を更新
                    String submittedFilename = Paths.get(newFilePart.getSubmittedFileName()).getFileName().toString();
                    newImageFilename = System.currentTimeMillis() + "_" + submittedFilename;
                    newFilePart.write(uploadPath + File.separator + newImageFilename);
                
                // 2. 新しい画像はなく、削除チェックボックスがオンか？
                } else if (deleteImage) {
                    // 古い画像があれば物理的に削除
                    if (currentImageFilename != null && !currentImageFilename.isEmpty()) {
                        File oldFile = new File(uploadPath, currentImageFilename);
                        if (oldFile.exists()) {
                            oldFile.delete();
                        }
                    }
                    newImageFilename = null; // DB上のファイル名をnullにする
                }

                // 最終的に決定したメッセージとファイル名でDBを更新
                dao.doUpdate(letterId, message, newImageFilename, loginUser.getUserId());

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            response.sendRedirect("LetterMain");

        } else {
            response.sendRedirect("Login");
        }
    }
}
