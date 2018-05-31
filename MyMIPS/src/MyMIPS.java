import java.io.IOException;

import br.ufrpe.deinfo.aoc.mips.MIPS;
import br.ufrpe.deinfo.aoc.mips.Simulator;
import br.ufrpe.deinfo.aoc.mips.State;
import jline.console.ConsoleReader;

public class MyMIPS implements MIPS {

	@SuppressWarnings("unused")
	private ConsoleReader console;
	
	public MyMIPS() throws IOException {
		this.console = Simulator.getConsole();
	}
	
	@Override
	public void execute(State state) throws Exception {
		
		Integer PC = state.getPC();
		
		if (PC.equals(0)) {
			state.writeRegister(28, 0x1800);
			state.writeRegister(29, 0x3ffc);
		}
		
		String instrucaoAtual =  completeToLeft(Integer.toBinaryString(state.readInstructionMemory(PC)), '0', 32);
		String op = instrucaoAtual.substring(0, 6);
		
		if (op.equals("000000")) {
			InstTipoR(instrucaoAtual, state);
		} else if (op.equals("000011") || op.equals("000010")) {
			InstTipoJ(instrucaoAtual, state);
		} else {
			InstTipoI(instrucaoAtual, state);
		}
		
		state.setPC(state.getPC() + 4);				
	}
	
	public static void main(String[] args) {
		try {
			Simulator.setMIPS(new MyMIPS());
			Simulator.setLogLevel(Simulator.LogLevel.INFO);
			Simulator.start();
		} catch (Exception e) {		
			e.printStackTrace();
		}		
	}

	public static String completeToLeft(String value, char c, int size) {
		if (value == null) {
			value = "";
		}
		String result = value;
		while ( result.length() < size ) {
			result = c + result;
		}
		return result;
	}
	
	public static String BinarioComSinal(String value, int size) {
		if (value == null) {
			value = "";
		}
		String result = value;
		
		if ((result.charAt(0)) == '1') {
			  while ( result.length() < size ) {
				result = '1' + result;
			}
			
			String novoResult = "";
			  
			for (int i = 31; i >= 0; i--) { //Complemento a 1
				if (result.charAt(i) == '1') {
					novoResult = '0' +  novoResult;
				} else {
					novoResult = '1' +  novoResult;
				}
			}
			
			String novoResultFinal = "";
			boolean carryIn = true;
			for (int i = 31; i >= 0; i--) { //Complemento a 2
				if (novoResult.charAt(i) == '1' && carryIn == true) {
					novoResultFinal = '0' + novoResultFinal;
					carryIn = true;
				} else if (carryIn == true){
					novoResultFinal = '1' + novoResultFinal;
					carryIn = false;
				} else {
					novoResultFinal = novoResult.charAt(i) + novoResultFinal;
				}
			}
			
			result = novoResultFinal;
			
			result = '-' + result;
			
		} else {
			while ( result.length() < size ) {
				result = '0' + result;
			}
		}
		
		return result;
	}
	
	public static String adduAuxiliar(String value, int size) {
		if (value == null) {
			value = "";
		}
		
		String rg = value;
			
			if ((rg.charAt(0)) == '1') { //completar 32 bits
				while ( rg.length() < size ) {
					rg = '1' + rg;
				}
			} else {
				while ( rg.length() < size ) {
					rg = '0' + rg;
				}
			}
			
		return rg;
	}
	
	public static String andAuxiliar(String rgRS, String rgRT, int size) {
		if (rgRS == null) {
			rgRS = "";
		}
		
		if (rgRT == null) {
			rgRT = "";
		}		
		
		String valorRS = rgRS;
		String valorRT = rgRT;
		
		if ((valorRS.charAt(0)) == '1') { //completar 32 bits
			while ( valorRS.length() < size ) {
				valorRS = '1' + valorRS;
			}
		} else {
			while ( valorRS.length() < size ) {
				valorRS = '0' + valorRS;
			}
		}
		
		if ((valorRT.charAt(0)) == '1') { //completar 32 bits
			while ( valorRT.length() < size ) {
				valorRT = '1' + valorRT;
			}
		} else {
			while ( valorRT.length() < size ) {
				valorRT = '0' + valorRT;
			}
		}
		
		String resultadoFinal = "";
		
		for (int i = 31; i >= 0; i--) {
			if((valorRS.charAt(i)) == '1' && (valorRT.charAt(i)) == '1') {
				resultadoFinal = '1' + resultadoFinal;
			} else {
				resultadoFinal = '0' + resultadoFinal;
			}
		}
		
		return resultadoFinal;
		
	}
	
	public static String jrAuxiliar(String rgRS, int size) {
		if (rgRS == null) {
			rgRS = "";
		}
		
		String valorRS = rgRS;
		
			if ((valorRS.charAt(0)) == '1') { //completar 32 bits
				while ( valorRS.length() < size ) {
					valorRS = '1' + valorRS;
				}
			} else {
				while ( valorRS.length() < size ) {
					valorRS = '0' + valorRS;
				}
			}
			
		return valorRS;
	}
	
	public static String norAuxiliar(String rgRS, String rgRT, int size) {
		if (rgRS == null) {
			rgRS = "";
		}
		
		if (rgRT == null) {
			rgRT = "";
		}	
		
		String valorRS = rgRS;
		String valorRT = rgRT;
		
		if ((valorRS.charAt(0)) == '1') { //completar 32 bits
			while ( valorRS.length() < size ) {
				valorRS = '1' + valorRS;
			}
		} else {
			while ( valorRS.length() < size ) {
				valorRS = '0' + valorRS;
			}
		}
		
		if ((valorRT.charAt(0)) == '1') { //completar 32 bits
			while ( valorRT.length() < size ) {
				valorRT = '1' + valorRT;
			}
		} else {
			while ( valorRT.length() < size ) {
				valorRT = '0' + valorRT;
			}
		}
		
		String resultadoFinal = "";
		
		for (int i = 31; i >= 0; i--) {
			if((valorRS.charAt(i)) == '0' && (valorRT.charAt(i)) == '0') {
				resultadoFinal = '1' + resultadoFinal;
			} else {
				resultadoFinal = '0' + resultadoFinal;
			}
		}
		
		return resultadoFinal;
	}
	
	public static String orAuxiliar(String rgRS, String rgRT, int size) {
		if (rgRS == null) {
			rgRS = "";
		}
		
		if (rgRT == null) {
			rgRT = "";
		}	
		
		String valorRS = rgRS;
		String valorRT = rgRT;
		
		if ((valorRS.charAt(0)) == '1') { //completar 32 bits
			while ( valorRS.length() < size ) {
				valorRS = '1' + valorRS;
			}
		} else {
			while ( valorRS.length() < size ) {
				valorRS = '0' + valorRS;
			}
		}
		
		if ((valorRT.charAt(0)) == '1') { //completar 32 bits
			while ( valorRT.length() < size ) {
				valorRT = '1' + valorRT;
			}
		} else {
			while ( valorRT.length() < size ) {
				valorRT = '0' + valorRT;
			}
		}
		
		String resultadoFinal = "";
		
		for (int i = 31; i >= 0; i--) {
			if((valorRS.charAt(i)) == '0' && (valorRT.charAt(i)) == '0') {
				resultadoFinal = '0' + resultadoFinal;
			} else {
				resultadoFinal = '1' + resultadoFinal;
			}
		}
		
		return resultadoFinal;
	}
	
	public static String sltAuxiliar(String value, int size) {
		if (value == null) {
			value = "";
		}
		
		String result = value;
		
		if ((result.charAt(0)) == '1') {
			  while ( result.length() < size ) {
				result = '1' + result;
			}
			
			String novoResult = "";
			  
			for (int i = 31; i >= 0; i--) { //Complemento a 1
				if (result.charAt(i) == '1') {
					novoResult = '0' +  novoResult;
				} else {
					novoResult = '1' +  novoResult;
				}
			}
			
			String novoResultFinal = "";
			boolean carryIn = true;
			for (int i = 31; i >= 0; i--) { //Complemento a 2
				if (novoResult.charAt(i) == '1' && carryIn == true) {
					novoResultFinal = '0' + novoResultFinal;
					carryIn = true;
				} else if (carryIn == true){
					novoResultFinal = '1' + novoResultFinal;
					carryIn = false;
				} else {
					novoResultFinal = novoResult.charAt(i) + novoResultFinal;
				}
			}
			
			result = novoResultFinal;
			
			result = '-' + result;
			
		} else {
			while ( result.length() < size ) {
				result = '0' + result;
			}
		}
		
		return result;
		
	}
	
	//aqui
	
	public static void InstTipoR (String instrucaoAtual, State state) {
		
		Integer rs = Integer.parseInt((instrucaoAtual.substring(6, 11)), 2);
		Integer rt = Integer.parseInt((instrucaoAtual.substring(11, 16)), 2);
		Integer rd = Integer.parseInt((instrucaoAtual.substring(16, 21)), 2);
		Integer shamt = Integer.parseInt((instrucaoAtual.substring(21, 26)), 2);
		String funct = instrucaoAtual.substring(26, 32);
		Integer valorRS = state.readRegister(rs);
		Integer valorRT = state.readRegister(rt);
		Integer resultado = 0;
		
		switch (funct) {
		case "100000":  //FUN플O ADD
				valorRS = Integer.parseInt((BinarioComSinal(Integer.toBinaryString(valorRS), 32)), 2);
				valorRT = Integer.parseInt(BinarioComSinal(Integer.toBinaryString(valorRT), 32), 2);
				resultado = valorRS + valorRT;
				state.writeRegister(rd, resultado);
			break;
			
		case "100001": 	//FUN플O ADDU
				valorRS = Integer.parseInt(adduAuxiliar(Integer.toBinaryString(valorRS), 32), 2);
				valorRT = Integer.parseInt(adduAuxiliar(Integer.toBinaryString(valorRT), 32), 2);
				resultado = valorRS + valorRT;
				state.writeRegister(rd, resultado);
			break;
			
		case "100100": //FUN플O AND
				resultado = Integer.parseInt(andAuxiliar(Integer.toBinaryString(valorRS), Integer.toBinaryString(valorRT), 32), 2);
				state.writeRegister(rd, resultado);
			break;
			
		case "001000":	//FUN플O JR
				resultado = Integer.parseInt(jrAuxiliar(Integer.toString(valorRS), 32), 2);
				state.setPC(resultado - 4);
			break;
			
		case "100111":	//FUN플O NOR
				resultado = Integer.parseInt(norAuxiliar(Integer.toBinaryString(valorRS), Integer.toBinaryString(valorRT), 32), 2);
				state.writeRegister(rd, resultado);
			break;
			
		case "100101":	//FUN플O OR
				resultado = Integer.parseInt(orAuxiliar(Integer.toBinaryString(valorRS), Integer.toBinaryString(valorRT), 32), 2);
				state.writeRegister(rd, resultado);
			break;
			
		case "101010":	//FUN플O SLT
			valorRS = Integer.parseInt(sltAuxiliar(Integer.toBinaryString(valorRS), 32), 2);
			valorRT = Integer.parseInt(sltAuxiliar(Integer.toBinaryString(valorRT), 32), 2);
			
			if (valorRS < valorRT) {
				resultado = 1;
			} else {
				resultado = 0;
			}
			
			state.writeRegister(rd, resultado);
			break;
			
		case "":
				
			break;

		default:
			break;
		}
		
	}
	
	public static void InstTipoI (String instrucaoAtual, State state) {
	
	}

	public static void InstTipoJ (String instrucaoAtual, State state) {
	
	}
}
