// PilhaDinamica.java
package ifgoiano.pilhadinamica;

import java.util.Scanner;

/**
 * Projeto: PilhaDinamica
 * Este programa verifica se expressões aritméticas estão com a parametrização
 * (uso de parênteses, colchetes e chaves) correta, utilizando uma
 * implementação de pilha dinâmica.
 *
 * @author Mario Henrique
 */
public class PilhaDinamica {

    // --- 1. Definição da Classe Interna 'No' (Node) ---
    // Esta classe representa cada elemento individual (nó) da pilha.
    // Ela armazena o dado (um caractere) e a referência ao nó abaixo dela.
    private static class No {
        private char dado;  // O caractere armazenado (ex: '(', '[', '{')
        private No proximo; // Referência para o próximo nó (o de baixo na pilha)

        /**
         * Construtor do Nó.
         * @param dado O caractere a ser armazenado.
         */
        public No(char dado) {
            this.dado = dado;
            this.proximo = null; // Por padrão, o próximo é nulo
        }
    }

    // --- 2. Definição da Classe Interna 'Pilha' (Stack) ---
    // Esta classe implementa a estrutura da Pilha Dinâmica, gerenciando os Nós.
    private static class Pilha {
        private No topo; // Referência para o nó no topo da pilha

        /**
         * Construtor da Pilha.
         * Inicializa a pilha como vazia (topo é nulo).
         */
        public Pilha() {
            this.topo = null;
        }

        /**
         * Verifica se a pilha está vazia.
         * @return true se o topo for nulo, false caso contrário.
         */
        public boolean isEmpty() {
            return this.topo == null;
        }

        /**
         * Adiciona um elemento (caractere) ao topo da pilha.
         * @param dado O caractere a ser empilhado.
         */
        public void push(char dado) {
            No novoNo = new No(dado);      // Cria o novo nó
            novoNo.proximo = this.topo;   // O novo nó aponta para o topo antigo
            this.topo = novoNo;           // O novo nó se torna o topo
        }

        /**
         * Remove e retorna o elemento do topo da pilha.
         * @return O caractere que estava no topo.
         * @throws RuntimeException se a pilha estiver vazia (Underflow).
         */
        public char pop() {
            if (isEmpty()) {
                // Em um verificador de expressão, tentar desempilhar de uma pilha vazia
                // (ex: na expressão ")") é um sinal de erro na expressão.
                // Poderíamos lançar uma exceção, mas para este algoritmo,
                // retornar um caractere nulo ('\0') simplifica o verificador.
                return '\0';
            }
            char dadoTopo = this.topo.dado; // Salva o dado do topo
            this.topo = this.topo.proximo;  // Move o topo para o nó de baixo
            return dadoTopo;                // Retorna o dado salvo
        }

        /**
         * Apenas consulta (olha) o elemento no topo, sem removê-lo.
         * @return O caractere no topo, ou '\0' se estiver vazia.
         */
        public char peek() {
            if (isEmpty()) {
                return '\0';
            }
            return this.topo.dado;
        }
    }

    // --- 3. Lógica de Verificação ---

    /**
     * Verifica se os delimitadores na expressão estão balanceados.
     *
     * @param expressao A string da expressão a ser verificada.
     * @return true se a expressão estiver correta, false caso contrário.
     */
    public static boolean verificarBalanceamento(String expressao) {
        Pilha pilha = new Pilha();

        // Itera por cada caractere da string da expressão
        for (int i = 0; i < expressao.length(); i++) {
            char c = expressao.charAt(i);

            // Se for um caractere de ABERTURA, empilha
            if (c == '(' || c == '[' || c == '{') {
                pilha.push(c);
            }
            // Se for um caractere de FECHAMENTO...
            else if (c == ')' || c == ']' || c == '}') {
                
                // Se a pilha estiver vazia ao encontrar um 'fecha',
                // significa que há um 'fecha' sem um 'abre' correspondente.
                // Ex: " ) ( " ou " ( ) ) "
                if (pilha.isEmpty()) {
                    return false; // Incorreto
                }

                // Desempilha o último caractere de ABERTURA que foi guardado
                char topo = pilha.pop();

                // Verifica se o par corresponde
                // Ex: Se o atual é ')' o topo DEVE ser '('
                
                if (c == ')' && topo != '(') {
                    return false; // Incorreto (Ex: "{ ( ] )" )
                }
                if (c == ']' && topo != '[') {
                    return false; // Incorreto (Ex: "( [ ) ]" )
                }
                if (c == '}' && topo != '{') {
                    return false; // Incorreto (Ex: "[ { ) }" )
                }
            }
            // Outros caracteres (números, operadores, espaços) são ignorados.
        }

        // Ao final da expressão, a pilha DEVE estar vazia.
        // Se sobrar algo na pilha, significa que um 'abre' não foi fechado.
        // Ex: " ( ( ) "
        return pilha.isEmpty();
    }

    // --- 4. Método Principal (Execução) ---

    /**
     * Método main para testar o verificador de expressões.
     * Lê expressões do teclado até que o usuário digite "sair".
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expressao;

        System.out.println("--- Verificador de Parametrização Correta (Pilha Dinâmica) ---");
        System.out.println("Use parênteses (), colchetes [] e chaves {}.");
        System.out.println("Digite 'sair' para terminar o programa.");
        System.out.println("------------------------------------------------------------");

        while (true) {
            System.out.print("\nDigite a expressão: ");
            expressao = scanner.nextLine();

            if (expressao.equalsIgnoreCase("sair")) {
                break;
            }

            // Chama o método de verificação
            if (verificarBalanceamento(expressao)) {
                System.out.println("Resultado: Correto");
            } else {
                System.out.println("Resultado: Incorreto");
            }
        }

        System.out.println("\nPrograma encerrado.");
        scanner.close();
    }
}