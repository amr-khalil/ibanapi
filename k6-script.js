/**
 * k6 script to validate a list of IBANs by sending HTTP POST requests to a local API.
 * @example
 * // Run the script with 10 virtual users for 300 seconds
 * // k6 run --vus 10 --duration 300s k6-script.js
 *
 */

import http from "k6/http";
import { sleep } from "k6";

/**
 * Default function executed by k6.
 * Sends HTTP POST requests to validate a list of IBANs.
 */
export default function () {
  const url = "http://localhost:80/api/iban/validate";
  const headers = { "Content-Type": "application/json" };

  const ibans = [
    "DE89370400440532013000",
    "GB82WEST12345698765432",
    "FR7630006000011234567890189",
    "NL91ABNA0417164300",
    "ES9121000418450200051332",
    "IT60X0542811101000000123456",
    "BE68539007547034",
    "CH9300762011623852957",
    "SE4550000000058398257466",
    "DK5000400440116243",
    "",
    "123",
    "122414284848488428281231232323123213231233212334",
    "DEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE",
  ];

  /**
   * Sends a POST request for each IBAN in the list.
   *
   * @param {string} iban - The IBAN to be validated.
   */
  ibans.forEach((iban) => {
    const payload = JSON.stringify({ iban: iban });
    http.post(url, payload, { headers: headers });
  });

  sleep(5);
}
