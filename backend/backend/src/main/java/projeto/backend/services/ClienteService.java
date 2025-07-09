package projeto.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.backend.dto.ClienteCadastroDTO;
import projeto.backend.model.Cliente;
import projeto.backend.model.Conta;
import projeto.backend.model.Endereco;
import projeto.backend.model.Usuario;
import projeto.backend.repository.ClienteRepository;
import projeto.backend.repository.ContaRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ContaService contaService;

    public Cliente cadastrarCliente(ClienteCadastroDTO dto){

        // Cria o usuário com os dados de login
        Usuario usuario = new Usuario();
        usuario.setLogin(dto.getLogin());
        usuario.setSenha(dto.getSenha());
        usuario.setTipo("CLIENTE");

        // Cria o endereço com os dados fornecidos
        Endereco endereco = new Endereco();
        endereco.setRua(dto.getRua());
        endereco.setCasa(dto.getCasa());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setLougradouro(dto.getLougradouro());
        endereco.setCep(dto.getCep());

        // Cria o cliente com os dados pessoais e associa o usuário e o endereço
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());
        cliente.setDataNascimento(dto.getDataNascimento());
        cliente.setFuncao(dto.getFuncao());
        cliente.setSalario(dto.getSalario());
        cliente.setUsuario(dto.getUsuario());
        cliente.setEndereco(dto.getEndereco());

        // Salva o cliente no banco (junto com usuário e endereço por causa do cascade)
        Cliente salvo = clienteRepository.save(cliente);

        // Cria a conta com base no salário e associa ao cliente salvo
        Conta conta = contaService.criarContaParaCliente(salvo);
        contaRepository.save(conta);

        // Retorna o cliente salvo (com ID preenchido)
        return salvo;
    }
}
