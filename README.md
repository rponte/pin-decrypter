Entendendo o que o código faz
-----------------------------
Quando compramos numa loja e efetuamos um pagamento via maquineta (POS) uma requisição de venda é enviada via rede e passa por vários serviços como sub-adquirente, adquirente, bandeira e por fim pelo emissor do cartão de crédito. Nesta requisição de venda, diversas informações são enviadas, entre elas o valor da compra, número de parcelas, número do cartão (PAN), senha digitada (PIN) etc.

No emissor, é onde justamente ocorre a autorização da venda. São várias validações de segurança, e uma delas é validar a senha digitada (PIN) na maquineta pelo usuário. Como a senha vem encriptada da maquineta, precisamos **desencripta-la** utilizando chaves de segurança.

Para isso funcionar, o meu sistema precisa ter obrigatoriamente em algum lugar:
1. as 3 component keys (são 3 chaves cadastradas no sistema por 3 pessoas distintas);
2. a chave ZPK encriptada (ela eh a chave utilizada para desencriptar o PIN);

Com elas eu sigo os passos abaixo para desencriptar a PIN:
1. gero a Master Key (ZMK) a partir das 3 component keys (internamente se faz um XOR entre elas);
2. desencripto a ZPK usando essa Master Key;
3. com a ZPK desencriptada em mãos, agora eu posso desencriptar a PIN;
4. ao desencriptar a PIN, eu não obtenho a PIN em texto plano, mas sim uma PIN Block (que eh o embaralhamento da PIN + PAN);
5. por fim, para obter a PIN em texto plano, eu uso o PAN para desembaralhar a PIN Block e assim extrair a senha;

(Leia isto para entender algumas das [siglas utilizadas](http://netzts.in/retail-payments-domain/hsm-lmk-zmk-tmk-pvk-cvk/))

## How to use:

```java
public class HowToDecryptAPIN {
 
    /**
     * Using fake data
     */
    public static void main(String[] args) {
    
	// PAN and PIN informed by the user (ie.: via POS or ATM)
        String pan = "4000340000000500"; // card number
	String encryptedPin = "FA8EA4D3FCB5466E"; // pin=2020
        
	// keys restored from our app
        EncryptedZpk encryptedZpk = new EncryptedZpk("A5FFF148D005286C2CAA46B785FCDD02");
        MasterKey masterKey = MasterKey.combined(
                            "B4089E65CF3ED07DF4D9EB31334B3494", // key1
                            "E2E9668244CE6E673040711F4B851420", // key2
                            "190F2C84CA89B09E8F8FA9D41671398B");// key3
        
	// now we can decrypt the PIN
        KeyStorage keyStorage = new SimpleKeyStorage() // or new DatabaseKeyStorage();
                                        .withMasterKey(masterKey)
                                        .withEncryptedZpk(encryptedZpk);
	
	PinDecrypter decrypter = new PinDecrypter(keyStorage);
	String pin = decrypter.decrypt(encryptedPin, pan);
    
        System.out.println("plain text PIN: " + pin);
    }
}
``
