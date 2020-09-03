# language:pt

Funcionalidade: Cliente integração


Cenário: Deve listar os clientes

			Dado que eu devo listar uma lista de clientes cadastrado
			Quando eu realizo uma chamada
			Então recebo uma lista de clientes
			Então recebo o status da lista cliente 200
			  
			  
Cenário: Deve cadastrar um cliente com sucesso

				Dado um cliente para cadastrar
				Então cadastro um cliente
				Então recebo o status do cliente 201

				
