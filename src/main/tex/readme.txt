1- Pegue o XML e remova todos os conteudos, deixando somente as tags;
	
	Ex:
	antes: 
	<veiculo>
		<ano>1984</ano>
	</veiculo>
	depois:
	<veiculo>
		<ano></ano>
	</veiculo>
	
2- Converta o XML para XSD (XMLSchema) com design type: Venetian Blind, existe algumas ferramentas disponiveis no mercado ou/e site de conversao Online;
	Ex:
	https://devutilsonline.com/xsd-xml/generate-xsd-from-xml
	

3- Execute o comando abaixo:
		xjc -d {diretorio saída} -p {package} {caminho onde está o xsd} -verbose
	Ex:
	C:\temp>C:\jdk1.7.0_79\bin\xjc -d . -p com.rci.omega2.async.tex.client.xsd \temp\ExtrairCotacoes.xsd -verbose
	
	
4- Exemplo usando o JAXB Unmarshaller:
			.
			.
			.
        try {

            File file = new File("C:/analise/ExtrairCotacoes.xml");

            JAXBContext jaxbContext = JAXBContext.newInstance(CalculosType.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            JAXBElement<CalculosType> rootElement = (JAXBElement<CalculosType>) jaxbUnmarshaller
                    .unmarshal(new StreamSource(file), CalculosType.class);

            CalculosType calculos = rootElement.getValue();

            System.out.println(calculos);

        } catch (Exception e) {
            e.printStackTrace();
        }
			.
			.
			.