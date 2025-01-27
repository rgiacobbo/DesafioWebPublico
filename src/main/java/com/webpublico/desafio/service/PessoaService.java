package com.webpublico.desafio.service;

import com.webpublico.desafio.models.PessoaModel;
import com.webpublico.desafio.repositories.PessoaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.CSVReader;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public static String validador(long n) {
        String str = String.valueOf(n);
        if (str.length() == 11) {
            return validaCpf(str)?"Pessoa Fisica": "invalido";
        } else if (str.length() == 14) {
            return validaCnpj(str)?"Pessoa Juridica": "invalido";
        }
        return "invalido";
    }

    public static String validadorSimples(long n) {
        String str = String.valueOf(n);
        if (str.length() == 11) {
            return "Pessoa Fisica";
        } else if (str.length() == 14) {
            return "Pessoa Juridica";
        }
        return "invalido";
    }

    private static boolean validaCpf(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false;
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int n1 = 11 - (soma % 11);
        n1 = (n1 > 9) ? 0 : n1;
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        int n2 = 11 - (soma % 11);
        n2 = (n2 > 9) ? 0 : n2;
        return n1 == (cpf.charAt(9) - '0') && n2 == (cpf.charAt(10) - '0');
    }

    private static boolean validaCnpj(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += (cnpj.charAt(i) - '0') * pesos1[i];
        }
        int n1 = 11 - (soma % 11);
        n1 = (n1 > 9) ? 0 : n1;
        soma = 0;
        for (int i = 0; i < 13; i++) {
            soma += (cnpj.charAt(i) - '0') * pesos2[i];
        }
        int n2 = 11 - (soma % 11);
        n2 = (n2 > 9) ? 0 : n2;
        return n1 == (cnpj.charAt(12) - '0') && n2 == (cnpj.charAt(13) - '0');
    }

    @Transactional
    public List<PessoaModel> cadastrarPessoas(MultipartFile file) throws Exception {
        List<PessoaModel> pessoas = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] linha;
            reader.readNext();
            while ((linha = reader.readNext()) != null) {
                PessoaModel pessoa = new PessoaModel();
                pessoa.setId(Long.parseLong(linha[0]));
                pessoa.setNome(linha[1]);
                pessoa.setNumeroDeCadastro(Long.parseLong(linha[2]));
                String tipo = validadorSimples(pessoa.getNumeroDeCadastro());
                if ("invalido".equals(tipo)) {
                    throw new IllegalArgumentException("CPF/CNPJ inválido." + pessoa.getNumeroDeCadastro());
                }
                pessoa.setIdentificador(tipo);
                if (pessoaRepository.existsByNumeroDeCadastro(pessoa.getNumeroDeCadastro())) {
                    throw new IllegalArgumentException("O número de cadastro já existe.");
                }
                 pessoaRepository.save(pessoa);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro no uploading: " + e.getMessage(), e);
        }
        return pessoas;
    }


    public PessoaModel salvarValidacaoSimples(PessoaModel pessoaModel) {
        String res = validadorSimples(pessoaModel.getNumeroDeCadastro());
        if(res.equals("invalido")){
            throw new IllegalArgumentException("Erro, não é valido.");
        }
        pessoaModel.setIdentificador(res);
        if (pessoaRepository.existsByNumeroDeCadastro(pessoaModel.getNumeroDeCadastro())) {
            throw new IllegalArgumentException("Erro, o numero de cadastro já existente.");
        }
        return pessoaRepository.save(pessoaModel);
    }

    public PessoaModel salvar(PessoaModel pessoaModel) {
        return pessoaRepository.save(pessoaModel);
    }

    public PessoaModel atualizaPessoa(PessoaModel pessoaModel) {
        return pessoaRepository.save(pessoaModel);
    }

    public List<PessoaModel>  listaTodasAsPessoas() {
        return pessoaRepository.findAll();
    }

    public Optional<PessoaModel> BuscaPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    public void excluir(PessoaModel pessoaModel){
        pessoaRepository.delete(pessoaModel);
    }

}
