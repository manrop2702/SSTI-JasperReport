package com.proj.SSTI;

import net.sf.jasperreports.engine.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/generateReport")
public class ReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String fileName = request.getParameter("filename");
            String userInput = request.getParameter("input");
            String dirPath = getServletContext().getRealPath("/WEB-INF/reports/");
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    new File(dirPath + "report.jrxml").getPath());

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("logoImage", dirPath+fileName);
            if (userInput != null) {
                parameters.put("userInput", userInput);
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=report.pdf");

            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
