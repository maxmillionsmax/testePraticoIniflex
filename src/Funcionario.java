////2 - Classe Funcionario extende Pessoa, atributos: salario(BigDecimal) e funcão(String)
//public class Funcionario extends Pessoa{
//	
//	private String funcao;
//	
//    private BigDecimal salario;
//    
//    private static final  BigDecimal SALARIO_MIN = BigDecimal.valueOf(1212);
//    
//    public Funcionario(String nome, String dataDeNascimento, BigDecimal salario, String funcao) {
//        super(nome, dataDeNascimento);
//        this.salario = salario;
//        this.funcao = funcao;
//    }
//
//    public BigDecimal getSalario() {
//        return salario;
//    }
//
//    public void setSalario(BigDecimal salario) {
//        this.salario = salario;
//    }
//
//    public String getFuncao() {
//        return funcao;
//    }
//
//    public void setFuncao(String funcao) {
//        this.funcao = funcao;
//    }
//    
//    public double obterSalariosMinimos(){
//    	return this.getSalario()
//    			.divide(SALARIO_MIN, 2,RoundingMode.HALF_UP)
//    			.doubleValue();
//    }
//   
//    public long obterIdade(){
//        LocalDate dataDeNascimento = super.StringToLocalDate(getDataDeNascimento());
//        return ChronoUnit.YEARS.between(dataDeNascimento, LocalDate.now());
//    }
//
//}