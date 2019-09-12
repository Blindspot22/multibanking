/**
 * Multibanking REST Api
 * Use a bank code (blz) ending with X00 000 00 like 300 000 00 to run aggainst the mock backend. Find the mock backend at ${hostname}:10010
 *
 * OpenAPI spec version: 5.1.3-SNAPSHOT
 * Contact: age@adorsys.de
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


/**
 * BankAccess account information
 */
export interface BankAccess { 
    /**
     * Bank name
     */
    readonly bankName?: string;
    /**
     * Categorize bookings
     */
    categorizeBookings?: boolean;
    /**
     * PSD2 consent id
     */
    readonly consentId?: string;
    /**
     * IBAN
     */
    iban: string;
    /**
     * Internal bank access id
     */
    readonly id?: string;
    /**
     * Provide anonymized bookings for machine learning
     */
    provideDataForMachineLearning?: boolean;
    /**
     * Store analytics
     */
    storeAnalytics?: boolean;
    /**
     * Store anonymized bookings
     */
    storeAnonymizedBookings?: boolean;
    /**
     * Store bookings
     */
    storeBookings?: boolean;
}