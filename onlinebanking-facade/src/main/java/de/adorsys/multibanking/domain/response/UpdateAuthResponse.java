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

package de.adorsys.multibanking.domain.response;

import de.adorsys.multibanking.domain.BankApi;
import de.adorsys.multibanking.domain.ChallengeData;
import de.adorsys.multibanking.domain.ScaStatus;
import de.adorsys.multibanking.domain.TanTransportType;
import lombok.Data;

import java.util.List;

@Data
public class UpdateAuthResponse {

    private BankApi bankApi;
    private List<TanTransportType> scaMethods;
    private ScaStatus scaStatus;
    private ChallengeData challenge;
    private String psuMessage;
}
