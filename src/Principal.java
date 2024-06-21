import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;


//1 - Classe Pessoa com os atribuos nome(String) e data nascimento(LocalDate) 
  class Pessoa {
	  
  private static final DateTimeFormatter FORMATACAO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private String nome;
  private LocalDate dataNascimento;

  public Pessoa(String nome, String dataNascimento) {
      this.nome = nome;
      this.dataNascimento = this.StringToLocalDate(dataNascimento);
  }

  public String getNome() {
      return nome;
  }

  public void setNome(String nome) {
      this.nome = nome;
  }

  public String getDataDeNascimento() {
      return this.localDateToString(dataNascimento);
  }

  public void setDataDeNascimento(String dataDeNascimento) {
      this.dataNascimento = this.StringToLocalDate(dataDeNascimento);
  }
 
  public LocalDate StringToLocalDate(String dataString){
      return LocalDate.parse(dataString, FORMATACAO_DATA);
  }

  public String localDateToString(LocalDate data){
      return data.format(FORMATACAO_DATA);
  }	
}

class Funcionario extends Pessoa{
	
	private String funcao;
	
    private BigDecimal salario;
    
    private static final  BigDecimal SALARIO_MIN = BigDecimal.valueOf(1212);
    
    public Funcionario(String nome, String dataDeNascimento, BigDecimal salario, String funcao) {
        super(nome, dataDeNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
    
    public double obterSalariosMinimos(){
    	return this.getSalario()
    			.divide(SALARIO_MIN, 2,RoundingMode.HALF_UP)
    			.doubleValue();
    }
   
    public long obterIdade(){
        LocalDate dataDeNascimento = super.StringToLocalDate(getDataDeNascimento());
        return ChronoUnit.YEARS.between(dataDeNascimento, LocalDate.now());
    }

}

//3 - Classe Principal pra execução das ações.
class Principal {
	
    public static List<Funcionario> funcionariosList = new ArrayList<>(10);
    
    public static Map<String, List<Funcionario>> agrupaFuncionario = new TreeMap<>();      

    public static void listarTodosFuncionarios(){
    	System.out.println("Nome\t\tData de nascimento\tSalário\t\tFunção");
        System.out.println("------          -------------------     ---------       ------------");
        DecimalFormat formatoDecimal = new DecimalFormat("#,##0.00");
        funcionariosList.forEach(funcionario -> {
            System.out.printf("%-15s %-23s R$ %-13s %s\n",
                    funcionario.getNome(),
                    funcionario.getDataDeNascimento(),
                    formatoDecimal.format(funcionario.getSalario()),
                    funcionario.getFuncao());
        });
    }
    
    public static void listarTodosFuncionariosPorNome(){
    	System.out.println("Nome:");      
    	System.out.println("-----"); 
        funcionariosList.forEach(funcionario -> {
            System.out.printf(funcionario.getNome()+"\n");
        });
    }
    
    public static void listarNomesESalariosFuncionarios(List<Funcionario> funcionariosList) {
    	System.out.println("Nome\t\tNovo salário");
        System.out.println("-----           -------------");
        DecimalFormat formatoDecimal = new DecimalFormat("#,##0.00");
        funcionariosList.forEach(funcionario -> {
            System.out.printf("%-15s R$ %-13s\n", funcionario.getNome(), formatoDecimal.format(funcionario.getSalario()));
        });
    }
    
    public static void deletarFuncionarioPeloNome(String nome){
    	Iterator<Funcionario> iterator = funcionariosList.iterator();
        while (iterator.hasNext()) {
            Funcionario funcionario = iterator.next();
            if (funcionario.getNome().equalsIgnoreCase(nome)) {
                iterator.remove();
            }
        }    	
    }

    public static void reajustarSalario(Double porcentagem){
    	double reajuste = 1 + porcentagem / 100;
    	funcionariosList.forEach(funcionario -> {
    	    var salario = funcionario.getSalario();
    	    funcionario.setSalario(salario.multiply(new BigDecimal(reajuste)));
    	});
    }

    public static void agruparFuncionaios(){
    	for (Funcionario funcionario : funcionariosList) {
    	    String funcao = funcionario.getFuncao();
    	    agrupaFuncionario.computeIfAbsent(funcao, k -> new ArrayList<>()).add(funcionario);
    	}
    }

        //3.5 Agrupar funcionarios por Função
    public static void listarFuncionariosPelaFuncao(Map<String, List<Funcionario>> agrupaFuncionario) {
        int maxNomeLength = agrupaFuncionario.values().stream()
                .flatMap(List::stream)
                .mapToInt(funcionario -> funcionario.getNome().length())
                .max()
                .orElse(0);

        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s\n",
                "Contador", "Coordenador", "Diretor", "Eletricista", "Gerente", "Operador", "Recepcionista");
        System.out.println("---------       ------------    --------        ------------    -------         --------        --------------");

        for (int i = 0; i < maxNomeLength; i++) {
            for (Map.Entry<String, List<Funcionario>> entry : agrupaFuncionario.entrySet()) {
                List<Funcionario> funcionarios = entry.getValue();
                String nome = i < funcionarios.size() ? funcionarios.get(i).getNome() : "";
                System.out.printf("%-15s ", nome);
            }
            System.out.println(); 
        }
    }
    
    public static void listarFuncionarioNascidoMes_10_12(List<Funcionario> funcionariosList) {
        System.out.println("Nome:              Data de nascimento:");
        System.out.println("-----              -------------------");
        for (Funcionario funcionario : funcionariosList) {
            LocalDate dataNascimento = LocalDate.parse(funcionario.getDataDeNascimento(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Month mesDeNascimento = dataNascimento.getMonth();
            if (mesDeNascimento.equals(Month.OCTOBER) || mesDeNascimento.equals(Month.DECEMBER)) {
                System.out.printf("%-20s %s\n", funcionario.getNome(), funcionario.getDataDeNascimento());
            }
        }
        System.out.println();
    }

    public static Funcionario encontrarFuncionarioMaisVelho(List<Funcionario> funcionariosList) {
        return funcionariosList.stream()
            .max(Comparator.comparing(Funcionario::obterIdade))
            .orElse(null);
    }

    public static void listarFuncionariosEmOrdemAlfabetica(List<Funcionario> funcionariosList) {
        Collections.sort(funcionariosList, Comparator.comparing(Pessoa::getNome));
    }

    public static BigDecimal somarSalariosDosFuncionarios(List<Funcionario> funcionariosList) {
        return funcionariosList.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private static final DecimalFormat formatoDecimal = new DecimalFormat("#,###.00");
    
    public static String formatarBigDecimal(BigDecimal valor) {
        return formatoDecimal.format(valor);
    }

    public static void listarNumeroDeSalariosMinimosDeCadaFuncionario(List<Funcionario> funcionariosList) {
        System.out.println("Nome\t\tSalários Mínimos");
        System.out.println("-----           ----------------");
        funcionariosList.forEach(funcionario -> {
            double salariosMinimos = funcionario.obterSalariosMinimos();
            System.out.printf("%-20s %.1f\n", funcionario.getNome(), salariosMinimos);
        });
    }
    
    public static String[][] funcionarioDados = {
            {"Maria","18/10/2000","2009.44","Operador"},
            {"João","12/05/1990","2284.38","Operador"},
            {"Caio","02/05/1961","9836.14","Coordenador"},
            {"Miguel","14/10/1988","19119.88","Diretor"},
            {"Alice","05/01/1995","2234.68","Recepcionista"},
            {"Heitor","19/11/1999","1582.72","Operador"},
            {"Arthur","31/03/1993","4071.84","Contador"},
            {"Laura","08/07/1994","3017.45","Gerente"},
            {"Heloísa","24/05/2003","1606.85","Eletricista"},
            {"Helena","02/09/1996","2799.93","Gerente"}
    }; 
    
    public static void main(String[] args) {

    	funcionariosList = Arrays.stream(funcionarioDados).map(dadosFuncionario -> new Funcionario(
        dadosFuncionario[0],
        dadosFuncionario[1],
        new BigDecimal(dadosFuncionario[2]), 
        dadosFuncionario[3]))
    	.collect(Collectors.toList());
        
    	System.out.println("Lista de todos funcionários: \n");
    	listarTodosFuncionarios();
    	
        deletarFuncionarioPeloNome("joão");
        System.out.println("\n3.2 - Removido Funcionario `João` da lista:\n");
                
        System.out.println("3.3 -Imprimir Lista de funcionários: \n");
        listarTodosFuncionarios();       

        reajustarSalario(10.0);
        System.out.println("\n3.4 - Lista atualizada com aumento de 10% no salário de todos os funcionarios: \n");
        listarNomesESalariosFuncionarios(funcionariosList);

        System.out.println("\n3.6 - Imprimir Listar funcionários por função: \n");
        agruparFuncionaios();
        listarFuncionariosPelaFuncao(agrupaFuncionario);
        System.out.println("\n");

        System.out.println("\n3.8 - Imprimir Funcionario que fazem aniversario nos meses 10 e/ou 12: \n");
        listarFuncionarioNascidoMes_10_12(funcionariosList);

        System.out.println("\n3.9 - Immprimir Funcionário com maior idade: \n");
        Funcionario funcionarioMaisVelho = encontrarFuncionarioMaisVelho(funcionariosList);        
        System.out.println("Nome:      Idade:");
        System.out.println("-----      -------");
        System.out.printf("%-10s %d anos\n", funcionarioMaisVelho.getNome(), funcionarioMaisVelho.obterIdade());
        
        
        System.out.println("\n3.10 - Imprimir Listar de funcionários em ordem alfabética: \n");
        listarFuncionariosEmOrdemAlfabetica(funcionariosList);
        listarTodosFuncionariosPorNome();

        System.out.println("\n3.11 - Imprimir Valor total de salários de todos os funcionários => R$" + formatarBigDecimal(somarSalariosDosFuncionarios(funcionariosList)));
        System.out.println("\n");
        System.out.println("\n3.12 - Imprimir Listar funcionários e quantidade de salários mínimos: \n");

        listarNumeroDeSalariosMinimosDeCadaFuncionario(funcionariosList);        
        
    }
}