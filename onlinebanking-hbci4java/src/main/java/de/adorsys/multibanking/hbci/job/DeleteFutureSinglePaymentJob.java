/*
 * Copyright 2018-2019 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.multibanking.hbci.job;

import de.adorsys.multibanking.domain.request.TransactionRequest;
import de.adorsys.multibanking.domain.transaction.FutureSinglePayment;
import de.adorsys.multibanking.hbci.HbciBpdCacheHolder;
import org.kapott.hbci.GV.GVTermUebSEPADel;
import org.kapott.hbci.GV_Result.HBCIJobResult;
import org.kapott.hbci.structures.Konto;
import org.kapott.hbci.structures.Value;

public class DeleteFutureSinglePaymentJob extends AbstractPaymentJob<FutureSinglePayment> {

    private String jobName;

    public DeleteFutureSinglePaymentJob(TransactionRequest<FutureSinglePayment> transactionRequest, HbciBpdCacheHolder bpdCacheHolder) {
        super(transactionRequest, bpdCacheHolder);
    }

    @Override
    GVTermUebSEPADel createHbciJob() {
        FutureSinglePayment singlePayment = transactionRequest.getTransaction();

        Konto src = getHbciKonto();

        Konto dst = new Konto();
        dst.name = singlePayment.getReceiver();
        dst.iban = singlePayment.getReceiverIban();
        dst.bic = singlePayment.getReceiverBic();

        jobName = GVTermUebSEPADel.getLowlevelName();

        GVTermUebSEPADel hbciJob = new GVTermUebSEPADel(dialog.getPassport(), jobName, getSepaVersion(), null);

        hbciJob.setParam("orderid", singlePayment.getOrderId());
        hbciJob.setParam("date", singlePayment.getExecutionDate().toString());

        hbciJob.setParam("src", src);
        hbciJob.setParam("dst", dst);
        hbciJob.setParam("btg", new Value(singlePayment.getAmount(),
            singlePayment.getCurrency()));
        if (singlePayment.getPurpose() != null) {
            hbciJob.setParam("usage", singlePayment.getPurpose());
        }

        hbciJob.verifyConstraints();

        return hbciJob;
    }

    @Override
    protected String getHbciJobName() {
        return jobName;
    }

    @Override
    public String orderIdFromJobResult(HBCIJobResult paymentGV) {
        return null;
    }

}
