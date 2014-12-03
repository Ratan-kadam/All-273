package hello

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class RResponse {

	
	  private var customer_name: String = ""

	  def getCustomer_name = customer_name

	  def setCustomer_name(req_customer_name: String) = customer_name = req_customer_name
	
}
