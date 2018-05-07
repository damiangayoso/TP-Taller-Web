package ar.edu.unlam.tallerweb1.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorOperacionesMatematicas {
	@RequestMapping(path = "/operacionMatematica/{numeroUno}/{operador}/{numeroDos}", method = RequestMethod.GET)
	public ModelAndView operacionMatematica(@PathVariable double numeroUno, @PathVariable String operador,@PathVariable double numeroDos) {
		ModelMap modelo = new ModelMap();
		double resultado = 0;
		
		modelo.put("numeroUno", numeroUno);
		modelo.put("operador", operador);
		modelo.put("numeroDos", numeroDos);
		
		switch (operador) {
        case "Sumar":
        				resultado = numeroUno + numeroDos;
        				break;
		
        case "Restar":
        				resultado = numeroUno - numeroDos;
        				break;

        case "Multiplicar":
        				resultado = numeroUno * numeroDos;
        				break;
	
        case "Dividir":
        				if(numeroDos == 0) {
        						modelo.put("mensaje", "No se puede dividir entre 0, intente con otro numero.");
            					return new ModelAndView("errorOperacion",modelo);
        				}
        				else {
        						resultado = numeroUno/numeroDos;
        						break;
        				}
        default:
        				modelo.put("mensaje", "El Operador que intenta usar no se encuentra disponible, utilize uno de los siguientes: Sumar, Restar, Multiplicar, Dividir");
        				return new ModelAndView("errorOperacion",modelo);

}

		modelo.put("resultado", resultado);
		
		return new ModelAndView("resultadoOperacion",modelo);
	}
}
