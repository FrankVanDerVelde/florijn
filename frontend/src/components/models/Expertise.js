export default class Participant {

    #id;
    #name;
    constructor(id, name) {
        this.#id = id;
        this.#name = name;
    }

    static getDummyExpertiseList() {
        const dummyExpertiseNames = ["Financieel Administratief", "Hypotheken" ,"Facturatie/Offertes","Secutirisaties","Boekhouding","CRM","Rapportage-tools","Conversietools", "Workflow", "Logistieke Processen", "Engineering", "Bouw & Infra", "Marketing", "(semi) Overheidsinstelling", "Web/App Development"];

        const dummyExpertiseList = dummyExpertiseNames.map((name, index) => {
            return {
                id: index,
                name: name
            }
        })
   
        return dummyExpertiseList;
    }
}