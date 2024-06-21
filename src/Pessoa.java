////1 - Classe Pessoa com os atribuos nome(String) e data nascimento(LocalDate) 
//public class Pessoa {
//    private static final DateTimeFormatter FORMATACAO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//    private String nome;
//    private LocalDate dataNascimento;
//
//    public Pessoa() {
//    }
//
//    public Pessoa(String nome, String dataNascimento) {
//        this.nome = nome;
//        this.dataNascimento = this.StringToLocalDate(dataNascimento);
//    }
//
//    public String getNome() {
//        return nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
//
//    public String getDataDeNascimento() {
//        return this.converterLocalDateParaString(dataNascimento);
//    }
//
//    public void setDataDeNascimento(String dataDeNascimento) {
//        this.dataNascimento = this.StringToLocalDate(dataDeNascimento);
//    }
//   
//    public LocalDate StringToLocalDate(String dataString){
//        return LocalDate.parse(dataString, FORMATACAO_DATA);
//    }
//
//    public String LocalDateToString(LocalDate data){
//        return data.format(FORMATACAO_DATA);
//    }	
//}