package projeto.backend.services.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import projeto.backend.dto.cliente.ClienteCadastroDTO;
import projeto.backend.model.cliente.Cliente;
import projeto.backend.model.cliente.Endereco;
import projeto.backend.model.cliente.Usuario;
import projeto.backend.model.conta.Conta;
import projeto.backend.repository.cliente.ClienteRepository;
import projeto.backend.repository.conta.ContaRepository;
import projeto.backend.services.conta.ContaService;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ContaService contaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Cliente cadastrarCliente(ClienteCadastroDTO dto){

        // Recupera o usuário do DTO e define o tipo
        Usuario usuario = dto.getUsuario();
        usuario.setTipo("CLIENTE");

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        // Recupera o endereço do DTO
        Endereco endereco = dto.getEndereco();

        // Cria o cliente com os dados pessoais e associa o usuário e o endereço
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());
        cliente.setDataNascimento(dto.getDataNascimento());
        cliente.setFuncao(dto.getFuncao());
        cliente.setSalario(dto.getSalario());
        cliente.setUsuario(usuario);
        cliente.setEndereco(endereco);

        // Salva o cliente no banco (junto com usuário e endereço por causa do cascade)
        Cliente salvo = clienteRepository.save(cliente);

        // Cria a conta com base no salário e associa ao cliente salvo
        Conta conta = contaService.criarContaParaCliente(salvo);
        contaRepository.save(conta);

        // Retorna o cliente salvo (com ID preenchido)
        return salvo;
    }
}
