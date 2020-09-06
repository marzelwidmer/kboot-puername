import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

contract {
    description = "/api/persons should return a JSON collection of Persons"

    request {
        url = url("/api/persons")
        method = GET
        headers {
            contentType = APPLICATION_JSON
        }
    }
    response {
        status = OK
        body = bodyFromFile("response.json")
        headers {
            contentType = APPLICATION_JSON
        }
    }
}
