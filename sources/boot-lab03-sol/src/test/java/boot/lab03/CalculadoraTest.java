package boot.lab03;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculadoraTest {

	@Autowired
	private Calculadora calculadora;
	
	@Test
	public void testSoma() {
		long result = calculadora.soma(10, 10);
		assertTrue(result == 20);
	}
	
	@Test
	public void testSubtracao() {
		long result = calculadora.subtracao(10, 10);
		assertTrue(result == 0);
	}
	
	@Test
	public void testMultiplicacao() {
		long result = calculadora.multiplicacao(10, 10);
		assertTrue(result == 100);
	}
	
	@Test
	public void testDivisao() {
		long result = calculadora.divisao(10, 10);
		assertTrue(result == 1);
	}

}
