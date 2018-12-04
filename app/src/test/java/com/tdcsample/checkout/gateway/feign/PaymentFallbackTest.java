package com.tdcsample.checkout.gateway.feign;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.tdcsample.checkout.gateway.feign.jsons.PaymentRequest;
import com.tdcsample.checkout.gateway.feign.jsons.PaymentResponse;
import com.tdcsample.checkout.gateway.feign.jsons.PaymentStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentFallbackTest {

    private PaymentFallback paymentFallback;

    @Before
    public void setup() {
        FixtureFactoryLoader.loadTemplates("com.tdcsample.checkout.templates");
        this.paymentFallback =  new PaymentFallback();
    }

    @Test
    public void executeValidProcessWithVisa(){
        //Given
        PaymentRequest request = Fixture.from(PaymentRequest.class).gimme("VISA");

        //When
        PaymentResponse response = this.paymentFallback.process(request);

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(PaymentStatus.NOT_SENT);
        assertThat(response.getTransactionId()).isEqualTo("internal-12345");
        assertThat(response.getDetails()).isEmpty();
    }

    @Test
    public void executeValidProcessWithBillet(){
        //Given
        PaymentRequest request = Fixture.from(PaymentRequest.class).gimme("BILLET");

        //When
        PaymentResponse response = this.paymentFallback.process(request);

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(PaymentStatus.NOT_SENT);
        assertThat(response.getTransactionId()).isEqualTo("internal-12345");
        assertThat(response.getDetails()).isEmpty();
    }

    @Test
    public void executeValidProcessWithNull(){
        //Given
        PaymentRequest request = null;

        //When
        PaymentResponse response = this.paymentFallback.process(request);

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(PaymentStatus.NOT_SENT);
        assertThat(response.getTransactionId()).isEqualTo("internal-12345");
        assertThat(response.getDetails()).isEmpty();
    }

}
