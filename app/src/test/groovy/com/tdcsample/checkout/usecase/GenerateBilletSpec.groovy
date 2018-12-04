package com.tdcsample.checkout.usecase

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import com.tdcsample.checkout.conf.TdcSampleProperties
import com.tdcsample.checkout.domain.BilletToPay
import spock.lang.Specification

import java.time.LocalDate

class GenerateBilletSpec extends Specification {

    private Integer daysToDueDate
    private BigDecimal additionPrice
    private GenerateBillet generateBillet

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("com.tdcsample.checkout.templates")
    }

    def setup() {
        daysToDueDate = 7
        additionPrice = 3.25

        TdcSampleProperties  tdcSampleProperties= new TdcSampleProperties()
        tdcSampleProperties.billet.setDaysDueDate(daysToDueDate)
        tdcSampleProperties.billet.setAdditionPrice(additionPrice)

        generateBillet = new GenerateBillet(tdcSampleProperties)
    }

    def "test exception when value to pay is null"() {
        given: ""
        Double valueToPay = null

        when: ""
        generateBillet.execute(valueToPay)

        then: ""
        IllegalArgumentException ex = thrown()
        ex.message == 'Value to pay cannot be null'
    }

    def "test generate billet to pay"() {
        given: ""
        double valueToPay = 35.5

        LocalDate dueDateExpected = LocalDate.now().plusDays(daysToDueDate)
        BigDecimal totalToPayExpected = 38.75

        when: ""
        BilletToPay billetToPay = generateBillet.execute(valueToPay)

        then: ""
        billetToPay != null
        billetToPay.number != ""
        billetToPay.dueDate == dueDateExpected
        billetToPay.value == totalToPayExpected
    }

    def "test generate sufix value"() {
        given: ""
        BigDecimal totalToPay = 38.75

        def sufixBilletNumberExpected = "00003875"

        when: ""
        String sufixBilletNumberActual = generateBillet.generateSufixBilletNumber(totalToPay)

        then: ""
        sufixBilletNumberActual == sufixBilletNumberExpected
    }
}
