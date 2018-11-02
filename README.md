# processo seletivo da STI - UFF 

**Sobre:**
	Este programa foi feito como [desafio](Desafio.md) do processo seletivo para STI UFF.  Foi utilizado Java 1.8.0_191, IDE Eclipse 4.9.0. 
	https://github.com/sti-uff/trabalhe-conosco
	
**Classes:**
	Foram utilizadas duas classes, uma classe Aluno que contém as informações do aluno. E a classe Main que possui os métodos utilizados e o programa principal.

**Problemas e Melhorias:**
	Uns dos problemas desse programa são que as tarefas não são atômicas. O programa deveria garantir que todas as etapas foram concluídas com sucesso ou caso algum processo falhe, deve garantir que vai restaurar as informações originais. E também a gestão do arquivo, pois sempre q se altera um aluno é necessário copiar todos os outros alunos. Pode ser resolvido com um banco de dados. 
