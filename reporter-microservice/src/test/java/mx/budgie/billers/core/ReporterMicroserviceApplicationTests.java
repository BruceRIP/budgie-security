package mx.budgie.billers.core;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mx.budgie.billers.reporter.util.ReporterUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.fill.JRFillInterruptedException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReporterMicroserviceApplicationTests {

	@Test
	public void contextLoads() {
		
	}

	public void reportTest() {
		List<DataBean> dataList = new ArrayList<>();
		dataList.add(new DataBean(12, "Tonelada", "Iden_1", "Identificacion_1", 10.5, 126.00));
		dataList.add(new DataBean(1, "Tonelada", "Iden_2", "Identificacion_2", 10.5, 126.00));
		dataList.add(new DataBean(4, "Kilo", "Iden_3", "Identificacion_3", 10.5, 126.00));
		dataList.add(new DataBean(3, "Newton", "Iden_4", "Identificacion_4", 10.5, 126.00));
		dataList.add(new DataBean(2, "Peso", "Iden_5", "Identificacion_5", 10.5, 126.00));
		dataList.add(new DataBean(120, "Tonelada", "Iden_1", "Identificacion_1", 10.5, 126.00));
		dataList.add(new DataBean(10, "Tonelada", "Iden_2", "Identificacion_2", 10.5, 126.00));
		dataList.add(new DataBean(40, "Kilo", "Iden_3", "Identificacion_3", 10.5, 126.00));
		dataList.add(new DataBean(30, "Newton", "Iden_4", "Identificacion_4", 10.5, 126.00));
		dataList.add(new DataBean(20, "Peso", "Iden_5", "Identificacion_5", 10.5, 126.00));
		dataList.add(new DataBean(130, "Tonelada", "Iden_1", "Identificacion_1", 10.5, 126.00));
		dataList.add(new DataBean(13, "Tonelada", "Iden_2", "Identificacion_2", 10.5, 126.00));
		dataList.add(new DataBean(44, "Kilo", "Iden_3", "Identificacion_3", 10.5, 126.00));
		dataList.add(new DataBean(35, "Newton", "Iden_4", "Identificacion_4", 10.5, 126.00));
		dataList.add(new DataBean(26, "Peso", "Iden_5", "Identificacion_5", 10.5, 126.00));
		dataList.add(new DataBean(132, "Tonelada", "Iden_1", "Identificacion_1", 10.5, 126.00));
		dataList.add(new DataBean(111, "Tonelada", "Iden_2", "Identificacion_2", 10.5, 126.00));
		dataList.add(new DataBean(411, "Kilo", "Iden_3", "Identificacion_3", 10.5, 126.00));
		dataList.add(new DataBean(354, "Newton", "Iden_4", "Identificacion_4", 10.5, 126.00));
		dataList.add(new DataBean(265, "Peso", "Iden_5", "Identificacion_5", 10.5, 126.00));
		try {		
			ReporterUtil util = new ReporterUtil();
			String absolutePath = util.getAbsolutePathFile("templates/cfdi_billers.jasper");
			String printFileName = util.fillReport(absolutePath,null, dataList);
			if(printFileName != null){				
//	             1- export to PDF
				util.exportPDF(printFileName,"/Users/brucewayne/Desktop/BudgieSoftware/sample_report.pdf");	            
	         }
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (JRFillInterruptedException e) {			
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	public class DataBean {

		private Integer cantidad;
		private String unidadMedida;
		private String identificacion;
		private String descripcion;
		private Double precioUnitario;
		private Double importe;

		public DataBean(Integer cantidad, String unidadMedida, String identificacion, String descripcion,
				Double precioUnitario, Double importe) {
			super();
			this.cantidad = cantidad;
			this.unidadMedida = unidadMedida;
			this.identificacion = identificacion;
			this.descripcion = descripcion;
			this.precioUnitario = precioUnitario;
			this.importe = importe;
		}

		public Integer getCantidad() {
			return cantidad;
		}

		public void setCantidad(Integer cantidad) {
			this.cantidad = cantidad;
		}

		public String getUnidadMedida() {
			return unidadMedida;
		}

		public void setUnidadMedida(String unidadMedida) {
			this.unidadMedida = unidadMedida;
		}

		public String getIdentificacion() {
			return identificacion;
		}

		public void setIdentificacion(String identificacion) {
			this.identificacion = identificacion;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public Double getPrecioUnitario() {
			return precioUnitario;
		}

		public void setPrecioUnitario(Double precioUnitario) {
			this.precioUnitario = precioUnitario;
		}

		public Double getImporte() {
			return importe;
		}

		public void setImporte(Double importe) {
			this.importe = importe;
		}

	}
}
