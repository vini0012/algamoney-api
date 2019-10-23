package com.example.algamoney.api.util;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.util.ResourceUtils;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RelatorioUtil {

    public static byte[] gerarRelatorio(Collection<?> collection, String nomeRelatorio, Map<String, Object> parametros) {
        try {
            String arquivo = ResourceUtils.getFile("classpath:relatorios/" + nomeRelatorio).getAbsolutePath();
            Map<String, Object> parametrosRelatorio = new HashMap<>();
            parametrosRelatorio.put("DATA_EMISSAO", DateUtil.getFormatada(new Date()));

            if (parametros != null) {
                parametrosRelatorio.putAll(parametros);
            }

            JasperPrint print = JasperFillManager.fillReport(arquivo, parametrosRelatorio, new JRBeanCollectionDataSource(collection));
            return JasperExportManager.exportReportToPdf(print);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] gerarRelatorio(Collection<?> collection, String nomeRelatorio) {
        return gerarRelatorio(collection, nomeRelatorio, null);
    }
}
