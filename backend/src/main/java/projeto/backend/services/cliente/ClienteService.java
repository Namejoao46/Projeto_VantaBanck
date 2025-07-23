package projeto.backend.services.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import projeto.backend.dto.cliente.ClienteCadastroDTO;
import projeto.backend.model.cliente.Cliente;
import projeto.backend.model.cliente.Endereco;
import projeto.backend.model.cliente.Usuario;
import projeto.backend.model.conta.Conta;
import projeto.backend.repository.cliente.ClienteRepository;
import projeto.backend.repository.cliente.UsuarioRepository;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Cliente cadastrarCliente(ClienteCadastroDTO dto){
        System.out.println("🔧 Chamando método cadastrarCliente...");
        System.out.println("➡️ Dados recebidos: " + dto);


        // Recupera o usuário do DTO e define o tipo
        Usuario usuario = dto.getUsuario();
        usuario.setTipo("CLIENTE");

        // ✅ Verifica se login já existe
        if(usuarioRepository.existsByLogin(usuario.getLogin())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "⚠️ Login já está em uso.");
        }

        // ✅ Verifica se CPF já existe
        if (clienteRepository.existsByCpf(dto.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "⚠️ CPF já cadastrado.");
        }

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        System.out.println("🔐 Senha criptografada com sucesso");

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
        System.out.println("💾 Cliente salvo: " + salvo.getId());

        // Cria a conta com base no salário e associa ao cliente salvo
        Conta conta = contaService.criarContaParaCliente(salvo);
        contaRepository.save(conta);

        salvo.setConta(conta);
        clienteRepository.save(salvo);

        System.out.println("Conta criada com saldo inicial: " + conta.getSaldo());
        
        // Retorna o cliente salvo (com ID preenchido)
        return salvo;
    }


    public void atualizarCliente(Long id, ClienteCadastroDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        // Verifica se o novo CPF ou login já está em uso por outro cliente
        if (!cliente.getCpf().equals(dto.getCpf()) &&
            clienteRepository.existsByCpf(dto.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "⚠️ CPF já está cadastrado.");
        }

        if (!cliente.getUsuario().getLogin().equals(dto.getUsuario().getLogin()) &&
            usuarioRepository.existsByLogin(dto.getUsuario().getLogin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "⚠️ Login já está em uso.");
        }

        // Atualiza os dados
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());
        cliente.setDataNascimento(dto.getDataNascimento());
        cliente.setFuncao(dto.getFuncao());
        cliente.setSalario(dto.getSalario());

        Usuario usuario = cliente.getUsuario();
        usuario.setLogin(dto.getUsuario().getLogin());
        usuario.setSenha(passwordEncoder.encode(dto.getUsuario().getSenha()));

        Endereco endereco = cliente.getEndereco();
        endereco.setRua(dto.getEndereco().getRua());
        endereco.setCidade(dto.getEndereco().getCidade());
        endereco.setCep(dto.getEndereco().getCep());
        endereco.setEstado(dto.getEndereco().getEstado());

        clienteRepository.save(cliente);
    }
}
