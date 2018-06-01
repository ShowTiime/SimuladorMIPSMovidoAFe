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
	
	public static String binarioUnsigned (string value, int size) {
		// Mesmo código da função adduAuxiliar que pedro fez, só deixei o nome de uma forma mais genérica
		//  pra evitar repetição de codigo
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
	
	public static String sltuAuxiliar(String value, int size) {
		if (value == null) {
			value = "";
		}
		
		String valorRS = value;
		
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
	
	public static String sllAuxiliar(String value, int size) {
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
		case "100000":  //FUNÇÃO ADD
				valorRS = Integer.parseInt((BinarioComSinal(Integer.toBinaryString(valorRS), 32)), 2);
				valorRT = Integer.parseInt(BinarioComSinal(Integer.toBinaryString(valorRT), 32), 2);
				resultado = valorRS + valorRT;
				state.writeRegister(rd, resultado);
			break;
			
		case "100001": 	//FUNÇÃO ADDU
				valorRS = Integer.parseInt(binarioUnsigned(Integer.toBinaryString(valorRS), 32), 2);
				valorRT = Integer.parseInt(binarioUnsigned(Integer.toBinaryString(valorRT), 32), 2);
				resultado = valorRS + valorRT;
				state.writeRegister(rd, resultado);
			break;
			
		case "100100": //FUNÇÃO AND
				resultado = Integer.parseInt(andAuxiliar(Integer.toBinaryString(valorRS), Integer.toBinaryString(valorRT), 32), 2);
				state.writeRegister(rd, resultado);
			break;
			
		case "001000":	//FUNÇÃO JR
				resultado = Integer.parseInt(jrAuxiliar(Integer.toString(valorRS), 32), 2);
				state.setPC(resultado - 4);
			break;
			
		case "100111":	//FUNÇÃO NOR
				resultado = Integer.parseInt(norAuxiliar(Integer.toBinaryString(valorRS), Integer.toBinaryString(valorRT), 32), 2);
				state.writeRegister(rd, resultado);
			break;
			
		case "100101":	//FUNÇÃO OR
				resultado = Integer.parseInt(orAuxiliar(Integer.toBinaryString(valorRS), Integer.toBinaryString(valorRT), 32), 2);
				state.writeRegister(rd, resultado);
			break;
			
		case "101010":	//FUNÇÃO SLT
			valorRS = Integer.parseInt(sltAuxiliar(Integer.toBinaryString(valorRS), 32), 2);
			valorRT = Integer.parseInt(sltAuxiliar(Integer.toBinaryString(valorRT), 32), 2);
			
			if (valorRS < valorRT) {
				resultado = 1;
			} else {
				resultado = 0;
			}
			
			state.writeRegister(rd, resultado);
			break;
			
		case "101011":	//FUNÇÃO SLTU
				valorRS = Integer.parseInt(sltuAuxiliar(Integer.toBinaryString(valorRS), 32), 2);
				valorRT = Integer.parseInt(sltuAuxiliar(Integer.toBinaryString(valorRT), 32), 2);
				
				if (valorRS < valorRT) {
					resultado = 1;
				} else {
					resultado = 0;
				}
				
				state.writeRegister(rd, resultado);
			break;
			
		case "000000":	//FUNÇÃO SLL
				valorRT = Integer.parseInt(sllAuxiliar(Integer.toBinaryString(valorRT), 32), 2);
				
				if (shamt != 0) {
					resultado = valorRT * 2;
					for (int i = 1; i < shamt; i++) {
						resultado *= 2;
					}
				} else {
					resultado = valorRT;
				}
				
				state.writeRegister(rd, resultado);
			break;
			
		case "000010": // FUNÇÂO SRL
				valorRT = Integer.parseInt(BinarioComSinal(Integer.toBinaryString(valorRT), 32), 2);
				if(shamt != 0) {
					resultado = valorRT >> shamt;
				
				}else {
					resultado = valorRT
				}
				state.writeRegister(rd, resultado);
			break;
			
		case "100010": //FUNÇÃO SUB
				valorRS = Integer.parseInt((BinarioComSinal(Integer.toBinaryString(valorRS), 32)), 2);
				valorRT = Integer.parseInt(BinarioComSinal(Integer.toBynaryString(valorRT), 32), 2);
				resultado = valorRS - valorRT;
				state.writeRegister(rd, resultado);
			break;
			
		case "100011": //FUNÇÃO SUBU
				valorRS = Integer.parseInt(binarioUnsigned(Integer.toBinaryString(valorRS), 32), 2);
				valorRT = Integer.parseInt(binarioUnsigned(Integer.toBinaryString(valorRT), 32), 2);
				resultado = valorRS - valorRT;
				state.writeRegister(rd, resultado);
			break;
			
		default:
			break;
		}
		
	}
	
	public static void InstTipoI (String instrucaoAtual, State state) {
		Integer rs = Integer.parseInt((instrucaoAtual.substring(6, 11)), 2);
		Integer rt = Integer.parseInt((instrucaoAtual.substring(11, 16)), 2);
		Integer constantOrAddress = Integer.parseInt(instrucaoAtual.substring(16, 32)), 2));
		Integer valorRS = state.readRegister(rs);
		Integer valorRT = state.readRegister(rt);
		Integer resultado = 0;
		
		switch(op) {
			case "001000": //FUNÇÃO ADDI CONFIRAM SE FIZ CERTO.
				if((Integer.toBinaryString(constantOrAddress).charAt(0)) == '1') {
					constantOrAddress = Integer.parseInt((completeToLeft(Integer.toBinaryString(constantOrAddress), '1', 32)), 2);
				}else {
					constantOrAddress = Integer.parseInt((completeToLeft(Integer.toBinaryString(constantOrAddress), '0', 32)), 2);
				}
				
				if((Integer.toBinaryString(valorRS).charAt(0)) == '1') {
					valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '1', 32), 2);
				}else {
					valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '0', 32), 2);
				}
				result = valorRS + constantOrAddress;
				state.writeRegister(rt, result);
			break;
			
			case "001001": //FUNÇÂO ADDIU
				constantOrAddress = Integer.parseInt((completeToLeft(Integer.toBinaryString(constantOrAddress), '0', 32)), 2);
				valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '0', 32), 2);
				
				result = valorRS + constantOrAddress;
				state.writeRegister(rt, result);
			break
			
			case "001100": //FUNÇÃO ANDI
				//o rs pode ser negativo por isso esses ifs.
				
				if((Integer.toBinaryString(valorRS).charAt(0)) == '1') {
					valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '1', 32), 2);
				}else {
					valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '0', 32), 2);
				}
				//Como a função ANDI ela estende o imediato com zeros, eu imagino que não seja necessário nenhum if.
				constantOrAddress = Integer.parseInt((completeToLeft(Integer.toBinaryString(constantOrAddress), '0', 32)), 2);
				
				result = valorRS & constantOrAddress;
				state.writeRegister(rt, result);
			break;
				
			case "001101": // FUNÇÃO ORI
				//o rs pode ser negativo por isso esses ifs.
				
				if((Integer.toBinaryString(valorRS).charAt(0)) == '1') {
					valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '1', 32), 2);
				}else {
					valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '0', 32), 2);
				}
				//Como a função ORI ela estende o imediato com zeros, eu imagino que não seja necessário nenhum if.
				constantOrAddress = Integer.parseInt((completeToLeft(Integer.toBinaryString(constantOrAddress), '0', 32)), 2);
				
				result = valorRS | constantOrAddress;
				state.writeRegister(rt, result);
			break;
				
			case "000100": //FUNÇÃO BEQ
				
				char aux = Integer.toBinaryString(constantOrAddress).charAt(0);
				String bitZeros = "00";
				String auxConstant = "";
				String fim = "";
				short cont = 14;
				
				
				if((Integer.toBinaryString(valorRS).charAt(0)) == '1') {
					valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '1', 32), 2);
				}else {
					valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '0', 32), 2);
				}
				
				if((Integer.toBinaryString(valorRT).charAt(0)) == '1') {
					valorRT = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRT), '1', 32), 2);
				}else {
					valorRT = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRT), '0', 32), 2);
				}
				
				
				while(cont > 0) {
					auxConstant += aux;
					cont--;
				}
				
				fim = auxConstante+constantOrAddress+bitZeros;
				constantOrAddress = Integer.parseInt((completeToLeft(fim, fim.charAt(0), 32)), 2);
				result = state.getPC()+constantOrAddress+4;
				
				if(valorRT == valorRS) {
					state.setPC(result);
				}
			break;
			
			case "000101": //FUNÇÃO BNE
				char aux = Integer.toBinaryString(constantOrAddress).charAt(0);
				String bitZeros = "00";
				String auxConstant = "";
				String fim = "";
				short cont = 14;
				
				
				if((Integer.toBinaryString(valorRS).charAt(0)) == '1') {
					valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '1', 32), 2);
				}else {
					valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '0', 32), 2);
				}
				
				if((Integer.toBinaryString(valorRT).charAt(0)) == '1') {
					valorRT = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRT), '1', 32), 2);
				}else {
					valorRT = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRT), '0', 32), 2);
				}
				
				
				while(cont > 0) {
					auxConstant += aux;
					cont--;
				}
				
				fim = auxConstante+constantOrAddress+bitZeros;
				constantOrAddress = Integer.parseInt((completeToLeft(fim, fim.charAt(0), 32)), 2);
				result = state.getPC()+constantOrAddress+4;
				
				if(valorRT != valorRS) {
					state.setPC(result);
				}
			break;
			
			case "001010": //FUNÇÃO STLI
				result = 0;
				if((Integer.toBinaryString(constantOrAddress).charAt(0) == '1') {
					constantOrAddress = Integer.parseInt((completeToLeft(Integer.toBinaryString(constantOrAddress), '1', 32)), 2);
				}else {
					constantOrAddress = Integer.parseInt((completeToLeft(Integer.toBinaryString(constantOrAddress), '0', 32)), 2);
				}
				
				if((Integer.toBinaryString(valorRS).charAt(0)) == '1') {
					valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '1', 32), 2);
				}else {
					valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '0', 32), 2);
				}
				
				if(valorRS < constantOrAddress) {
					result = 1;
				}
				
				state.writeRegister(rt, result);
			break;
			
			case "001011": //FUNÇÃO SLTIU
				result = 0;
				constantOrAddress = Integer.parseInt((completeToLeft(Integer.toBinaryString(constantOrAddress), '0', 32)), 2);
				valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '0', 32), 2);

				if(valorRS < constantOrAddress) {
					result = 1;
				}
				
				state.writeRegister(rt, result);
				
			
			break;
				
			default:
				break;
		
		
		}
		
	
	}

	public static void InstTipoJ (String instrucaoAtual, State state) {
	
	}