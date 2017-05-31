package boot.lab03;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculadoraMalucaTest {

	@MockBean
	private Calculadora calculadora;
	
	@Test
	public void testSoma() {
		when(calculadora.soma(10, 10)).thenReturn(10l);
		long result = calculadora.soma(10, 10);
		assertTrue(result == 10);
	}
	
	@Test
	public void testSubtracao() {
		when(calculadora.subtracao(10, 10)).thenReturn(10l);
		long result = calculadora.subtracao(10, 10);
		assertTrue(result == 10);
	}
	
	@Test
	public void testMultiplicacao() {
		when(calculadora.multiplicacao(10, 10)).thenReturn(10l);
		long result = calculadora.multiplicacao(10, 10);
		assertTrue(result == 10);
	}
	
	@Test
	public void testDivisao() {
		when(calculadora.divisao(10, 10)).thenReturn(10l);
		long result = calculadora.divisao(10, 10);
		assertTrue(result == 10);
	}

}
