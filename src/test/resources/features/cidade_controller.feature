# language :pt

Funcionalidade: Cidade controller integração
			
			Como usuario 
			Quero realizar as chamadas do controller da cidade
			
						
Cenário: Deve listar cidades e estados

			Então dado um chamado para o listarCidades, entao eu recebo uma lista
			

Cenário: Cadastrar uma cidade

		Dado um nome de cidade e estado deve ser cadastrado
		Então recebo um DTO
		
Cenário: Cadastrar uma cidade já existente

		Então dado um nome de cidade e estado deve ser cadastrado "PORTO ALEGRE" , "RIO GRANDE DO SUL" retorna uma exception
		
Cenário: Buscar cidade com sucesso

		Dado um nome de uma cidade "PORTO ALEGRE"
		Então deve retornar a cidade informada
		
		
Cenário: Buscar uma cidade que não foi encontrado

		Então dado um nome de um cidade que não foi cadastrada "IJUI" retorna uma exception
		

Cenário: Buscar um estado com sucesso

		Dado um nome de um estado "RIO GRANDE DO SUL"
		Então deve retornar uma lista de cidades desse estado
		
Cenário: Buscar um estado sem sucesso

		Então dado um nome de um estado que não foi cadastrado "SANTA CATARINA", deve retornar uma exception
		
		
Cenário: Deletar uma cidade dos cadastros
		
		Dado um nome de uma cidade que deve ser apagado "PASSO FUNDO"
		Então deve retornar uma mensagem "Deletada com sucesso"
		

Cenário: Deletar uma cidade que não esteja cadastrado

		Então dado uma cidade que não esteja nos cadastros, deve retornar a mensagem "SAO PAULO"
		
			

