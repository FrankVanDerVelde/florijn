package com.hva.ewa.team2.backend.data.skill;

import com.hva.ewa.team2.backend.domain.models.skill.Expertise;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.skill.SkillGroup;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Deprecated
public class InMemorySkillRepository implements MemorySkillRepository {

    public static void main(String[] args) {
        new InMemorySkillRepository();
    }

    private ArrayList<Skill> skills = new ArrayList<>();

    private ArrayList<SkillGroup> skillGroups = new ArrayList<>();

    private ArrayList<Expertise> expertises = new ArrayList<>();

    public InMemorySkillRepository() {
        String[] skillNames = new String[]{"MS Office Access",
                "MS Office Access VBA",
                "MS Office Excel",
                "MS Office Excel VBA",
                "MS Power Query",
                "MS Powerpivot",
                "MS Office Word",
                "MS Office Word VBA",
                "MS Office Outlook",
                "MS Office Outlook VBA",
                "MS Office VBA",
                "MS SQL-Server",
                "MS SQL-Server Stored Procedures",
                "MS SQL-Server Views",
                "MY SQL",
                "MY SQL Workbench",
                "MS Azure",
                "Oracle",
                "Microsoft Dynamics AX",
                "Windows PowerShell (Core)",
                ".NET Framework",
                "XML - XAML",
                "Filemaker",
                "Filemaker Script",
                "Filemaker Server",
                "MS VB.NET",
                "MS Visual Basic",
                "Microsoft Dynamics 365",
                "Microsoft Dynamics 365 for Operations",
                "Microsoft Dynamics 365 for Business Applications",
                "Sharepoint",
                "Javascript",
                "Java",
                "PhP",
                "ASP.NET",
                "Google Apps ",
                "Google Apps Script",
                "Flow",
                "HTML",
                "CSS",
                "C#",
                "C++/CLI (Managed)",
                "F#",
                "Q#",
                ".NET Core",
                "Angular/AngularJS",
                "Bootstrap",
                "Mendix",
                "OutSystems",
                "PowerApps",
                "Power Automate",
                "Power Platform",
                "Tableau",
                "Qlik",
                "SAP",
                "SAS",
                "Oracle",
                "Salesforce",
                "Thoughtspot",
                "Yellowfin",
                "Sisense",
                "Microstrategy",
                "TIBCO Software",
                "Looker",
                "Information Builders",
                "Overall",
                "DAX",
                "M Language",
                "Grafisch",
                "Power Query",
                "Power BI Beheer",
                "Datamodellering",
                "Data analyse",
                "Agile",
                "Scrum",
                "Lean",
                "Kanban",
                "Extreme Programming (XP)"};

        String[] groupNames = new String[]{"OFFICE FRONT-END", "BACK-END", "DATABASE", "WEB BASED FRONT-END", "Business Intelligence","Power BI", "Werkwijze"};
        Integer[] skillPositions = {11, 11, 9, 21, 13, 8, 5};

        for (int i = 0; i < skillNames.length; i++) {
            this.skills.add(new Skill(i, skillNames[i], "Your ability to use " + skillNames[i]));
        }

        int lastPosition = 0;
        for (int i = 0; i < groupNames.length; i++) {
            SkillGroup tempSkillGroup = new SkillGroup(i, groupNames[i], "This is the group for " + groupNames[i]);

            for (int i2 = 0; i2 < skillPositions[i]; i2++) {
                tempSkillGroup.add(skills.get(lastPosition));
                lastPosition++;
            }

            this.skillGroups.add(tempSkillGroup);
        }

        String[] expertiseNames = new String[]{"Financieel Administratief", "Hypotheken" ,"Facturatie/Offertes","Secutirisaties","Boekhouding","CRM","Rapportage-tools","Conversietools", "Workflow", "Logistieke Processen", "Engineering", "Bouw & Infra", "Marketing", "(semi) Overheidsinstelling", "Web/App Development"};

        for (int i = 0; i < expertiseNames.length; i++) {
            this.expertises.add(new Expertise(i, expertiseNames[i]));
        }
    }

    @Override
    public ArrayList<Skill> findAllSkills() {
        return this.skills;
    }

    @Override
    public Skill findSkillById(int id) {
        return this.skills.stream().filter(skill -> skill.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public ArrayList<SkillGroup> findAllSkillGroups() {
        return skillGroups;
    }

    @Override
    public SkillGroup findGroupById(int id) {
        return this.skillGroups.stream().filter(group -> group.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public Skill getSkillById(int id) {
        return skills.stream().filter(skill -> skill.getId() == id).findFirst().orElse(null);
    }

    @Override
    public SkillGroup getGroupBySkillId(int id) {
        Skill skill = getSkillById(id);
        return skillGroups.stream().filter(group -> group.getSkills().contains(skill)).findFirst().orElse(null);
    }

    @Override
    public Expertise getExpertiseById(int id) {
        return expertises.stream().filter(expertise -> expertise.getId() == id).findFirst().orElse(null);
    }

    @Override
    public ArrayList<Expertise> getAllExpertises() {
        return this.expertises;
    }


}
