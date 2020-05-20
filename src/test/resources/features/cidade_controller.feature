# language :pt

Funcionalidade: Cidade integração
			
			Como usuario 
			Quero realizar as chamadas do controller da cidade
			
						
Cenário: Deve listar cidades e estados
			Dado que estou no listar cidades
			Quando eu realizado uma chamada
			Então recebo uma lista
			Então recebo o status da lista 200
			
		

Cenário: Deve cadastrar uma cidade com sucesso

		Dado uma cidade para cadastrar
		Então cadastro uma cidade
		Então recebo o status 201
		
Cenário: Deve cadastrar uma cidade sem sucesso

		Dado uma cidade para cadastrar que já existe
		Então cadastro uma cidade já existente
		
Cenário: Deve realizar uma buscar por uma cidade

		Dado uma cidade para realizar uma busca
		Quando eu realizar a busca
		Então recebo uma cidade
		Então recebo o status 200
		
Cenário: Deve realizar uma buscar por uma cidade que não existe no banco

		Dado uma cidade para realizar uma busca que não existe
		Entao eu realizo um busca por uma cidade que não existe no banco
		

Cenário: Deve realizar uma buscar por um estado

			Dado um estado que devo realizar a busca
			Quando eu realizar a busca do estado
			Então recebo uma lista
			Então recebo o status da lista 200

Cenário: Deve realizar uma buscar por um estado que não existe no banco

		Dado um estado para realizar uma busca que não existe
		Então eu realizo um busca por um estado que não existe no banco
