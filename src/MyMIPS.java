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
	
	static boolean PC4 = true;
	
	@Override
	public void execute(State state) throws Exception {
		
		Integer PC = state.getPC();
		PC4 = true;
		
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
		
		if (PC4) {
			state.setPC(state.getPC() + 4);	
		}
		PC4 = true;
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
	
	
	public static String BinarioComSinal(String value, int size) { //Modificada ***
		if (value == null) {
			value = "";
		}
		
		String result = value;
		
		if (result.length() != 32)  {
			while ( result.length() < size ) {
				result = '0' + result;
			} 
		}
		
		if (result.charAt(0) == '1'){

		/*
		if ((result.charAt(0)) == '1') {
			  while ( result.length() < size ) {
				result = '1' + result;
			}
		*/	
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
		}

		return result;
	}
	
	/*
	public static String binarioUnsigned (String value, int size) {
		// Mesmo c�digo da fun��o adduAuxiliar que pedro fez, s� deixei o nome de uma forma mais gen�rica
		//  pra evitar repeti��o de codigo
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
	*/
	
	
	public static String andAuxiliar(String rgRS, String rgRT, int size) {
		if (rgRS == null) {
			rgRS = "";
		}
		
		if (rgRT == null) {
			rgRT = "";
		}		
		
		String valorRS = rgRS;
		String valorRT = rgRT;
		
		
		while ( valorRS.length() < size ) {
			valorRS = '0' + valorRS;
		}
		
		while ( valorRT.length() < size ) {
			valorRT = '0' + valorRT;
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
		
			while ( valorRS.length() < size ) {
				valorRS = '0' + valorRS;
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
		
		while ( valorRS.length() < size ) {
			valorRS = '0' + valorRS;
		}
		
		while ( valorRT.length() < size ) {
			valorRT = '0' + valorRT;
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
		
		while ( valorRS.length() < size ) {
			valorRS = '0' + valorRS;
		}
		
		while ( valorRT.length() < size ) {
			valorRT = '0' + valorRT;
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
	
	/*
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
	*/
	
	/*
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
	*/
	
	/*
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
	*/
	
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
		case "100000":  //FUN��O ADD
				valorRS = Integer.parseInt((BinarioComSinal(Integer.toBinaryString(valorRS), 32)), 2);
				valorRT = Integer.parseInt(BinarioComSinal(Integer.toBinaryString(valorRT), 32), 2);
				resultado = valorRS + valorRT;
				state.writeRegister(rd, resultado);
			break;
			
		case "100001": 	//FUN��O ADDU
				valorRS = Integer.parseInt(BinarioComSinal(Integer.toBinaryString(valorRS), 32), 2);
				valorRT = Integer.parseInt(BinarioComSinal(Integer.toBinaryString(valorRT), 32), 2);
				resultado = valorRS + valorRT;
				state.writeRegister(rd, resultado);
			break;
			
		case "100100": //FUN��O AND
				resultado = Integer.parseInt(BinarioComSinal(andAuxiliar(Integer.toBinaryString(valorRS), Integer.toBinaryString(valorRT), 32), 32), 2);
				state.writeRegister(rd, resultado);
			break;
			
		case "001000":	//FUN��O JR
				resultado = Integer.parseInt(jrAuxiliar(Integer.toBinaryString(valorRS), 32), 2);
				state.setPC(resultado);
				PC4 = false;
			break;
			
		case "100111":	//FUN��O NOR			
				resultado = Integer.parseInt(BinarioComSinal(norAuxiliar(Integer.toBinaryString(valorRS), Integer.toBinaryString(valorRT), 32), 32), 2);
				state.writeRegister(rd, resultado);
			break;
			
		case "100101":	//FUN��O OR
				resultado = Integer.parseInt(BinarioComSinal(orAuxiliar(Integer.toBinaryString(valorRS), Integer.toBinaryString(valorRT), 32), 32), 2); 
				state.writeRegister(rd, resultado);
			break;
			
		case "101010":	//FUN��O SLT
			
			if (valorRS < valorRT) {
				resultado = 1;
			} else {
				resultado = 0;
			}
			
			state.writeRegister(rd, resultado);
			break;
			
		case "101011":	//FUN��O SLTU
				long valorRSBig = Long.parseLong(completeToLeft(Integer.toBinaryString(valorRS), '0', 32), 2);
				long valorRTBig = Long.parseLong(completeToLeft(Integer.toBinaryString(valorRT), '0', 32), 2);
				
				if (valorRSBig < valorRTBig) {
					resultado = 1;
				} else {
					resultado = 0;
				}
				
				state.writeRegister(rd, resultado);
			break;
			
		case "000000":	//FUN��O SLL
				valorRT = Integer.parseInt(BinarioComSinal(Integer.toBinaryString(valorRT), 32), 2);
				
				if (shamt != 0) {
					resultado = valorRT << shamt;
					//for (int i = 1; i < shamt; i++) {
					//	resultado *= 2;
					//}
				} else {
					resultado = valorRT;
				}
				
				state.writeRegister(rd, resultado);
			break;
			
		case "000010": // FUN��O SRL
				valorRT = Integer.parseInt(BinarioComSinal(Integer.toBinaryString(valorRT), 32), 2);
				if(shamt != 0) {
					resultado = valorRT >> shamt;
				}else {
					resultado = valorRT;
				}
				state.writeRegister(rd, resultado);
			break;
			
		case "100010": //FUN��O SUB
				valorRS = Integer.parseInt((BinarioComSinal(Integer.toBinaryString(valorRS), 32)), 2);
				valorRT = Integer.parseInt(BinarioComSinal(Integer.toBinaryString(valorRT), 32), 2);
				resultado = valorRS - valorRT;
				state.writeRegister(rd, resultado);
			break;
			
		case "100011": //FUN��O SUBU
				valorRS = Integer.parseInt((BinarioComSinal(Integer.toBinaryString(valorRS), 32)), 2);
				valorRT = Integer.parseInt(BinarioComSinal(Integer.toBinaryString(valorRT), 32), 2);
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
		Integer constantOrAddress = Integer.parseInt(instrucaoAtual.substring(16, 32), 2);
		Integer valorRS = state.readRegister(rs);
		Integer valorRT = state.readRegister(rt);
		//Integer valorConstant = state.readRegister(constantOrAddress);
		Integer result = 0;
		String singExtImm = "";
		String subStringRT = "";
		String StringRT = "";
		Integer valorSingExtImm = 0;
		Integer dadoMemoria = 0;
		String opcode = instrucaoAtual.substring(0, 6);
		String texto = "";
		
		switch(opcode) {
			case "001000": //FUN��O ADDI CONFIRAM SE FIZ CERTO. ***
				texto = instrucaoAtual.substring(16, 32);
				if((texto.charAt(0)) == '1') {
					constantOrAddress = Integer.parseInt(BinarioComSinal(completeToLeft(instrucaoAtual.substring(16, 32), '1', 32), 32), 2);
				}else {
					constantOrAddress = Integer.parseInt(BinarioComSinal(instrucaoAtual.substring(16, 32), 32), 2);
				}
				
				texto = instrucaoAtual.substring(6, 11);
				if((texto.charAt(0)) == '1') {
					valorRS = Integer.parseInt(BinarioComSinal(completeToLeft(instrucaoAtual.substring(6, 11), '1', 32), 32), 2);
				}else {
					valorRS = Integer.parseInt(BinarioComSinal(instrucaoAtual.substring(6, 11), 32), 2);
				}
				
				//constantOrAddress = Integer.parseInt(BinarioComSinal(Integer.toBinaryString(constantOrAddress), 32), 2);
				//valorRS = Integer.parseInt(BinarioComSinal(Integer.toBinaryString(valorRS), 32), 2);
				result = valorRS + constantOrAddress;
				state.writeRegister(rt, result);
			break;
			
			case "001001": //FUN��O ADDIU
				constantOrAddress = Integer.parseInt(BinarioComSinal(completeToLeft(instrucaoAtual.substring(16, 32), '1', 32), 32), 2);
				valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '0', 32), 2);
				
				result = valorRS + constantOrAddress;
				state.writeRegister(rt, result);
			break;
			
			case "001100": //FUN��O ANDI
				texto = instrucaoAtual.substring(16, 32);
				if((texto.charAt(0)) == '1') {
					constantOrAddress = Integer.parseInt(BinarioComSinal(completeToLeft(instrucaoAtual.substring(16, 32), '1', 32), 32), 2);
				}else {
					constantOrAddress = Integer.parseInt(BinarioComSinal(instrucaoAtual.substring(16, 32), 32), 2);
				}
				
				texto = instrucaoAtual.substring(6, 11);
				if((texto.charAt(0)) == '1') {
					valorRS = Integer.parseInt(BinarioComSinal(completeToLeft(instrucaoAtual.substring(6, 11), '1', 32), 32), 2);
				}else {
					valorRS = Integer.parseInt(BinarioComSinal(instrucaoAtual.substring(6, 11), 32), 2);
				}
				
				result = (valorRS & constantOrAddress);
				state.writeRegister(rt, result);
			break;
				
			case "001101": // FUN��O ORI
				constantOrAddress = Integer.parseInt(BinarioComSinal(completeToLeft(instrucaoAtual.substring(16, 32), '0', 32), 32), 2);
				valorRS = Integer.parseInt(BinarioComSinal(completeToLeft(instrucaoAtual.substring(6, 11), '0', 32), 32), 2);
				
				
				result = (valorRS | constantOrAddress);
				state.writeRegister(rt, result);
			break;
				
			case "000100": //FUN��O BEQ
				texto = instrucaoAtual.substring(16, 32);
				char aux = texto.charAt(0);
				String bitZeros = "00";
				String auxConstant = "";
				String fim = "";
				short cont = 14;
				
				
				while(cont > 0) {
					auxConstant += aux;
					cont--;
				}
				fim = auxConstant+texto+bitZeros; 
				constantOrAddress = Integer.parseInt(BinarioComSinal(fim, 32), 2);
				result = state.getPC()+constantOrAddress+4;
				
				if(valorRT == valorRS) {
					state.setPC(result);
					PC4 = false;
				}
				
			break;
			
			case "000101": //FUN��O BNE
				texto = instrucaoAtual.substring(16, 32);
				char aux2 = texto.charAt(0);
				String bitZeros2 = "00";
				String auxConstant2 = "";
				String fim2 = "";
				short cont2 = 14;
				
				
				while(cont2 > 0) {
					auxConstant2 += aux2;
					cont2--;
				}
				fim2 = auxConstant2+texto+bitZeros2; 
				constantOrAddress = Integer.parseInt(BinarioComSinal(fim2, 32), 2);
				result = state.getPC()+constantOrAddress+4;
				
				if(valorRT != valorRS) {
					state.setPC(result);
					PC4 = false;
				}
			break;
			
			case "001010": //FUN��O STLI
				/*
				texto = instrucaoAtual.substring(16, 32);
				if((texto.charAt(0)) == '1') {
					constantOrAddress = Integer.parseInt(BinarioComSinal(completeToLeft(instrucaoAtual.substring(16, 32), '1', 32), 32), 2);
				}else {
					constantOrAddress = Integer.parseInt(BinarioComSinal(instrucaoAtual.substring(16, 32), 32), 2); 
					
					
				}				
				////
				
				texto = instrucaoAtual.substring(6, 11);
				if((texto.charAt(0)) == '1') {
					valorRS = Integer.parseInt(BinarioComSinal(completeToLeft(instrucaoAtual.substring(6, 11), '1', 32), 32), 2);										
				}else {
					valorRS = Integer.parseInt(BinarioComSinal(completeToLeft(instrucaoAtual.substring(6, 11), '1', 32), 32), 2);
				}
				
				String texto2 = instrucaoAtual.substring(16, 32);
				if((texto2.charAt(0)) == '1') {
					valorConstant = Integer.parseInt(BinarioComSinal(completeToLeft(instrucaoAtual.substring(16, 32), '1', 32), 32), 2);										
				}else {
					valorConstant = Integer.parseInt(BinarioComSinal(completeToLeft(instrucaoAtual.substring(16, 32), '1', 32), 32), 2);
				}*/
				
				texto = instrucaoAtual.substring(16, 32);
				if((texto.charAt(0)) == '1') {
					constantOrAddress = Integer.parseInt(BinarioComSinal(completeToLeft(instrucaoAtual.substring(16, 32), '1', 32), 32), 2);
				}else {
					constantOrAddress = Integer.parseInt(BinarioComSinal(instrucaoAtual.substring(16, 32), 32), 2);
				}
				
				texto = instrucaoAtual.substring(6, 11);
				if((texto.charAt(0)) == '1') {
					valorRS = Integer.parseInt(BinarioComSinal(completeToLeft(instrucaoAtual.substring(6, 11), '1', 32), 32), 2);
				}else {
					valorRS = Integer.parseInt(BinarioComSinal(instrucaoAtual.substring(6, 11), 32), 2);
				}
				
				if(valorRS > constantOrAddress) { //GAMBIARRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
					result = 1;
				}
				
				state.writeRegister(rt, result);
				
				
			break;
			
			case "001011": //FUN��O SLTIU
				result = 0;
				constantOrAddress = Integer.parseInt((completeToLeft(Integer.toBinaryString(constantOrAddress), '0', 32)), 2);
				valorRS = Integer.parseInt(completeToLeft(Integer.toBinaryString(valorRS), '0', 32), 2);

				if(valorRS < constantOrAddress) {
					result = 1;
				}
				
				state.writeRegister(rt, result);
				
			break;

			case "100100": // FUN��O LBU
				singExtImm = instrucaoAtual.substring(16,32);
					if (singExtImm.charAt(0) == '1') {
						singExtImm = completeToLeft(singExtImm, '1', 32);
					} else {
						singExtImm = completeToLeft(singExtImm, '0', 32);
					}
					
					valorSingExtImm = Integer.parseInt(singExtImm, 2);
					
					dadoMemoria = state.readWordDataMemory(valorRS + valorSingExtImm);
					
					Integer byteRT = Integer.parseInt(completeToLeft((Integer.toBinaryString(dadoMemoria)).substring(24, 32), '0', 32), 2);
					
					state.writeRegister(rt, byteRT);
				break;

			case "100101": // FUN��O LHU
				singExtImm = instrucaoAtual.substring(16,32);
					if (singExtImm.charAt(0) == '1') {
						singExtImm = completeToLeft(singExtImm, '1', 32);
					} else {
						singExtImm = completeToLeft(singExtImm, '0', 32);
					}
					
					valorSingExtImm = Integer.parseInt(singExtImm, 2);
					
					dadoMemoria = state.readWordDataMemory(valorRS + valorSingExtImm);
					
					Integer halfRT = Integer.parseInt(completeToLeft((Integer.toBinaryString(dadoMemoria)).substring(16, 32), '0', 32), 2);
					
					state.writeRegister(rt, halfRT);
				break;
				
			case "001111": //FUN��O LUI
					String adress = instrucaoAtual.substring(16, 32);
					valorRT = Integer.parseInt((adress + "0000000000000000"), 2); // 16 0's
					
					state.writeRegister(rt, valorRT);
				break;
				
			case "100011": //FUN��O LW
				singExtImm = instrucaoAtual.substring(16,32);
				if (singExtImm.charAt(0) == '1') {
					singExtImm = completeToLeft(singExtImm, '1', 32);
				} else {
					singExtImm = completeToLeft(singExtImm, '0', 32);
				}
				
				valorSingExtImm = Integer.parseInt(singExtImm, 2);
				
				dadoMemoria = state.readWordDataMemory(valorRS + valorSingExtImm);

				state.writeRegister(rt, dadoMemoria);
				
				break;
				
			case "101000": //FUN��O SB
				singExtImm = instrucaoAtual.substring(16,32);
				if (singExtImm.charAt(0) == '1') {
					singExtImm = completeToLeft(singExtImm, '1', 32);
				} else {
					singExtImm = completeToLeft(singExtImm, '0', 32);
				}
				
				valorSingExtImm = Integer.parseInt(singExtImm, 2);
				
				subStringRT = (Integer.toBinaryString(state.readRegister(rt))).substring(24, 32);

				Integer dadoByteMemoria = state.readWordDataMemory(valorRS + valorSingExtImm);
				
				StringRT = ((Integer.toBinaryString(dadoByteMemoria)).substring(0, 24)) + subStringRT;
				
				valorRT = Integer.parseInt(StringRT, 2);
				
				state.writeWordDataMemory((valorRS + valorSingExtImm), valorRT);
				
				break;
				
			case "101001": //FUN��O SH
				singExtImm = instrucaoAtual.substring(16,32);
				if (singExtImm.charAt(0) == '1') {
					singExtImm = completeToLeft(singExtImm, '1', 32);
				} else {
					singExtImm = completeToLeft(singExtImm, '0', 32);
				}
				
				valorSingExtImm = Integer.parseInt(singExtImm, 2);
				
				subStringRT = (Integer.toBinaryString(state.readRegister(rt))).substring(16, 32);

				Integer dadoHalfMemoria = state.readWordDataMemory(valorRS + valorSingExtImm);
				
				StringRT = ((Integer.toBinaryString(dadoHalfMemoria)).substring(0, 16)) + subStringRT;
				
				valorRT = Integer.parseInt(StringRT, 2);
				
				state.writeWordDataMemory((valorRS + valorSingExtImm), valorRT);
				
				break;
				
			case "101011": //FUN��O SW
				singExtImm = instrucaoAtual.substring(16,32);
				if (singExtImm.charAt(0) == '1') {
					singExtImm = completeToLeft(singExtImm, '1', 32);
				} else {
					singExtImm = completeToLeft(singExtImm, '0', 32);
				}
				
				valorSingExtImm = Integer.parseInt(singExtImm, 2);
				
				state.writeWordDataMemory((valorRS + valorSingExtImm), state.readRegister(rt));
				
				break;	
			
			default:
				break;
			}
	}
	
	public static void InstTipoJ (String instrucaoAtual, State state) {
		String opcode = instrucaoAtual.substring(0, 6);
		String adress = instrucaoAtual.substring(6, 32);
		Integer jumpAddress = 0;
		
		switch (opcode) {
		case "000010": // FUN��O J
				jumpAddress = Integer.parseInt((Integer.toString(state.getPC() + 4)).substring(0, 4) + adress + "00");
				state.setPC(jumpAddress);
				PC4 = false;
			break;
				
		case "000011": // FUN��O JAL
				state.writeRegister(31, (state.getPC() + 4));
				jumpAddress = Integer.parseInt((Integer.toString(state.getPC() + 4)).substring(0, 4) + adress + "00");
				state.setPC(jumpAddress);
				PC4 = false;			
			break;
			
		default:
			break;
		}
	}
}