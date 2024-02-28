package com.proj.SSTI;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "FileUploadServlet", value = "/upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (ServletFileUpload.isMultipartContent(request)) {
            String fileName="";
            try {
                List<FileItem> multipart = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

                for (FileItem item : multipart) {
                    if (!item.isFormField()) {
                        fileName = new File(item.getName()).getName();
                        String uploadDirectory = getServletContext().getRealPath("/WEB-INF/reports/");
                        File uploadFile = new File(uploadDirectory + fileName);
                        if (uploadFile.exists()) {
                            uploadFile.delete();
                        }
                        item.write(uploadFile);
                    }
                }

                response.sendRedirect("/SSTI_war/generateReport?filename=" + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.getWriter().write("Invalid request");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

