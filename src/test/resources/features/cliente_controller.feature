# language:pt

Funcionalidade: Cliente Controller integração


Cenário: Listar clientes

			  Então dado um chamado para o listarCliente, entao eu recebo uma lista
			  
			  
Cenário: Cadastro de cliente

				Dado um nome,sobrenome,data de nascimento,sexo e cidade onde mora
				Então eu realizo o cadastro de um novo cliente
				
Cenário: Cadastro de cliente já existente

				 Então um nome,sobrenome,data de nascimento,sexo e cidade onde mora e esse cliente já foi cadastrado, entao retorna um exception com a a mensagem
			  	
Cenário: Buscar por nome completo 
			
				Dado um cliente com nome e sobrenome "FULANO", "TAL" e ao realizar a busca
				Então  eu recebo as informações desse cliente
				
Cenário: Buscar por nome completo não existente
			
				Então dado um cliente com nome e sobrenome "FABIO", "KOPEZINSKI" e ao realizar a busca não existir, então deve retornar um exception
				
				
Cenário: Buscar clientes com o mesmo nome

				Dado um nome "FULANO"
				Então deve retornar um lista com o nome informado possuindo todos os clientes com o mesmo nome
				
Cenário: Buscar clientes que o nome não foi cadastrado

				Então dado um nome que não possui cadastrado "FABIO", então retorna um exception
				
Cenário: Deletar cliente por nome

				Dado um nome que será deletado no banco
				Então ao deletar é exibido a mensagem "Deletado com sucesso"
				
Cenário: Deletar cliente por nome que não existe

				Então dado um nome a ser deletado que não esteja cadastrado "FABIO", "KOPEZINSKI" então deve retornar um exception

Cenário: Atualizar cliente

				Dado um cliente que queira atualizar o seu nome "FABIO", "KOPEZINSKI"
				Então deve retornar o nome do cliente atualizado
				
				