package ServidorPersistenteJDBC;

import java.sql.Timestamp;
import java.util.ArrayList;

import Dominio.MarcacaoPonto;
import Dominio.RegularizacaoMarcacaoPonto;

/**
 *
 * @author  Fernanda Quit�rio e Tania Pous�o
 */
public interface IMarcacaoPontoPersistente {
  public boolean alterarMarcacaoPonto(MarcacaoPonto marcacaoPonto);
  public boolean apagarMarcacaoPonto(int chaveMarcacao);
  public boolean apagarMarcacaoPonto(Timestamp dataMarcacao);
  public boolean apagarMarcacoesPonto();
  public ArrayList consultarMarcacoesPonto(ArrayList listaFuncionarios, ArrayList listaCartoes, ArrayList listaEstados, Timestamp dataInicio, Timestamp dataFim);
  public ArrayList consultarMarcacoesPontoErros(String estado); //n�o ser� preciso!!
  public boolean escreverMarcacaoPonto(MarcacaoPonto marcacaoPonto);
  public boolean escreverMarcacoesPonto(ArrayList listaMarcacoesPonto);
  public MarcacaoPonto lerMarcacaoPonto(int chaveMarcacao);
  public MarcacaoPonto lerMarcacaoPonto(Timestamp dataMarcacao, int numFuncionario);
  public ArrayList lerMarcacoesPonto();  
  public ArrayList lerMarcacoesPonto(int numCartao);  
  public ArrayList obterMarcacoesPonto(int numFuncionario, int numCartao, Timestamp dataFim);
  public ArrayList obterMarcacoesPonto(int numCartao, Timestamp dataInicio, Timestamp dataFim);
  public int ultimoCodigoInterno();  
  
	public boolean alterarMarcacaoPontoRegularizacao(RegularizacaoMarcacaoPonto regularizacao);
	public boolean alterarMarcacaoPontoEscreverRegularizacao(RegularizacaoMarcacaoPonto regularizacao);
	public int escreverMarcacaoPontoRegularizacao(MarcacaoPonto marcacaoPonto, RegularizacaoMarcacaoPonto regularizacao);
}