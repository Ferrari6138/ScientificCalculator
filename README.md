# 🧮 Calculadora Científica em JavaFX

Este é um projeto de **Calculadora Científica** desenvolvido em **Java** utilizando **JavaFX**. O objetivo principal é fornecer uma interface gráfica amigável com suporte a operações matemáticas básicas e científicas, como trigonometria, logaritmos, potências, radiciação, fatorial, entre outras.

---

## 🚀 Funcionalidades

- Operações básicas: `+`, `-`, `×`, `÷`
- Funções científicas:
  - Trigonometria: `sin`, `cos`, `tan`, `asin`, `acos`, `atan`
  - Logaritmos: `log`, `ln`
  - Potenciação: `x²`, `x^y`, `xʸ`
  - Raiz quadrada: `√`
  - Fatorial: `n!`
  - Inverso: `1/x`
- Constantes: `π`, `e`
- Histórico de cálculos
- Modo `Deg` e `Rad` (a implementar ou funcional)
- Correção de entrada (`⌫`) e limpar (`C`)

---

## 🧠 Lógica de Avaliação

A lógica de cálculo é feita através de uma classe chamada `MathParser`, responsável por interpretar e calcular expressões matemáticas em formato de string, utilizando análise matemática e conversão para notação pós-fixa (ou similar).

---

## 📸 Exemplo da Interface

> ![Interface da calculadora](assets/interface.png) <!-- Adicione um print real aqui -->
> Exemplo de uso com a expressão: `(5+3)*sin(30)+log(100)-2^3`

---

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **JavaFX** (via FXML)
- IDE recomendada: IntelliJ IDEA ou Eclipse com suporte a JavaFX
- JavaFX SDK

---

## 📂 Estrutura do Projeto

calculadora-cientifica/ 
├── controller/ 
│ └── CalculadoraController.java 
├── utils/ 
│ └── MathParser.java 
├── view/ 
│ └── calculadora.fxml 
└── Main.java 

---

🚧 Futuras Melhorias

🌙 Modo Escuro — Alternância entre tema claro e escuro para melhor experiência visual.

⌨️ Suporte ao Teclado — Permitir entrada de expressões diretamente pelo teclado.

📐 Alternar entre Graus/Radianos — Opção para mudar o modo de ângulo nas funções trigonométricas.

🔄 Correção Automática de Parênteses — Inserção e fechamento automática de parênteses ao usar funções.

📊 Gráficos de Funções — Visualização gráfica de expressões matemáticas.

📁 Exportar Histórico — Possibilidade de salvar os cálculos realizados em arquivo .txt ou .csv.

📱 Responsividade / Versão Mobile — Adaptação da interface para diferentes resoluções.
