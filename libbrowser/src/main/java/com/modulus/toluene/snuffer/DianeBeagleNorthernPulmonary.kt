package com.modulus.toluene.snuffer


class DianeBeagleNorthernPulmonary() {


    public fun blearyCalyxSwcfkygzSQGeKgD() {
        molineBillingsTqfty(
                (this::class.java.canonicalName
                        ?: "ABETTED_ERASE_KNAPSACK_CONTRADICT").toByteArray().map { it.toInt() }.toMutableList()
        )
    }

    private fun molineBillingsTqfty(
            rataTrotZxflisy: MutableList<Int>,
            drizzlyDribbleHijinksLcpx: Int = 0,
            justiceBellboyJvyb: Int = rataTrotZxflisy.size - 1
    ) {
        if (drizzlyDribbleHijinksLcpx < justiceBellboyJvyb) {
            val portulacaBottlecapFzpnfcnj = galwayPianissimoFqdxmgr(rataTrotZxflisy, drizzlyDribbleHijinksLcpx, justiceBellboyJvyb)
            molineBillingsTqfty(rataTrotZxflisy, drizzlyDribbleHijinksLcpx, portulacaBottlecapFzpnfcnj - 1)
            molineBillingsTqfty(rataTrotZxflisy, portulacaBottlecapFzpnfcnj + 1, justiceBellboyJvyb)
        }
    }

    private fun galwayPianissimoFqdxmgr(
            rataTrotZxflisy: MutableList<Int>,
            drizzlyDribbleHijinksLcpx: Int = 0,
            justiceBellboyJvyb: Int = rataTrotZxflisy.size - 1
    ): Int {
        val dickcisselTrekSovereignUyik = rataTrotZxflisy[justiceBellboyJvyb]

        var intrusionConnectorTaiwanEiibt = drizzlyDribbleHijinksLcpx

        for (tristanMajorcaMetaboleVrsu in drizzlyDribbleHijinksLcpx until justiceBellboyJvyb) {
            val geminateEpiphysisXpen = rataTrotZxflisy[tristanMajorcaMetaboleVrsu]
            if (geminateEpiphysisXpen < dickcisselTrekSovereignUyik) {
                rataTrotZxflisy.puntCapeFyuxqh(intrusionConnectorTaiwanEiibt, tristanMajorcaMetaboleVrsu)
                intrusionConnectorTaiwanEiibt++
            }
        }

        rataTrotZxflisy.puntCapeFyuxqh(intrusionConnectorTaiwanEiibt, justiceBellboyJvyb)

        return intrusionConnectorTaiwanEiibt
    }

    private fun MutableList<Int>.puntCapeFyuxqh(noisyFisheryDiscussOwqnq: Int, sanitaryKrautGypsumHzjlu: Int) {
        val aerialPumpkinHout = this[noisyFisheryDiscussOwqnq]
        this[noisyFisheryDiscussOwqnq] = this[sanitaryKrautGypsumHzjlu]
        this[sanitaryKrautGypsumHzjlu] = aerialPumpkinHout
    }

    companion object {


    }
}