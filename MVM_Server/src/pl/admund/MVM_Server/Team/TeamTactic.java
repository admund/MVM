package pl.admund.MVM_Server.Team;

public class TeamTactic 
{
	public static enum enumAtackStr { Mieszany, Mocny, Sredni, Lekki };
	public static enum enumAtackOri { Mieszany, Po_prostej, Skos, Ostry_skos, Kiwka };
	public static enum enumSerwStr { Mieszany, Mocny, Sredni, Lekki };
	public static enum enumSerwOri { Mieszany, Przyjmujacy1, Przyjmujacy2, Libero, Do_lini, Miedzy_dwoch, Skrot, Bomba };
	public static enum enumRecQual { Mieszany, Dokladnie, Do_gory };
	public static enum enumRecArea { Normalny, Wiecej, Mniej };
	public static enum enumBlock3 { Mieszany, Tak, Nie };
	public static enum enumBlockOpt { Mieszany, Prosta, Skos, Srodek, Intuicja };
	public static enum enumRozStyle { Mieszany, Prosty, Kombinacyjny };
	public static enum enumRozPref { Mieszany, Przyjmujacy1, Przyjmujacy2, Srodkowy, Atakujacy, Druga_linia };
	
	//team
    enumAtackStr teamAtackStr;
    enumAtackOri teamAtackOri;
    enumSerwStr teamSerwStr;
    enumSerwOri teamSerwOri;
    enumRecQual teamRecQual;
    enumRecArea teamRecArea;
    enumBlock3 teamBlock3;
    enumBlockOpt teamBlockOp;
    enumRozStyle teamRozStyl;
    enumRozPref teamRozPref;
	
	//att
    enumAtackStr atAtackStr;
    enumAtackOri atAtackOri;
    enumSerwStr atSerwStr;
    enumSerwOri atSerwOri;
    enumBlock3 atBlock3;
    enumBlockOpt atBlockOp;
	
	//rec1
	enumAtackStr rec1AtackStr;
	enumAtackOri rec1AtackOri;
	enumSerwStr rec1SerwStr;
	enumSerwOri rec1SerwOri;
	enumRecQual rec1RecQual;
	enumRecArea rec1RecArea;
	enumBlock3 rec1Block3;
	enumBlockOpt rec1BlockOp;
	
	//rec2
	enumAtackStr rec2AtackStr;
	enumAtackOri rec2AtackOri;
	enumSerwStr rec2SerwStr;
	enumSerwOri rec2SerwOri;
	enumRecQual rec2RecQual;
	enumRecArea rec2RecArea;
	enumBlock3 rec2Block3;
	enumBlockOpt rec2BlockOp;
	
	//block1
	enumAtackStr block1AtackStr;
	enumAtackOri block1AtackOri;
	enumSerwStr block1SerwStr;
	enumSerwOri block1SerwOri;
	enumBlock3 block1Block3;
	enumBlockOpt block1BlockOp;

	//block2
	enumAtackStr block2AtackStr;
	enumAtackOri block2AtackOri;
	enumSerwStr block2SerwStr;
	enumSerwOri block2SerwOri;
	enumBlock3 block2Block3;
	enumBlockOpt block2BlockOp;
	
	//roz
	enumSerwStr rozSerwStr;
	enumSerwOri rozSerwOri;
	enumBlock3 rozBlock3;
	enumBlockOpt rozBlockOp;
	enumRozStyle rozRozStyl;
	enumRozPref rozRozPref;
	
	//lib
	enumRecQual libRecQual;
	enumRecArea libRecArea;
	
	public static TeamTactic creatRandomTeamTactic()
	{
		//System.out.print("creatRandomTeamTactic\n");
		
		TeamTactic tmpTeamTactic = new TeamTactic();
		
		tmpTeamTactic.teamAtackStr = enumAtackStr.Mieszany;
		tmpTeamTactic.teamAtackOri = enumAtackOri.Mieszany;
		tmpTeamTactic.teamSerwStr = enumSerwStr.Mieszany;
		tmpTeamTactic.teamSerwOri = enumSerwOri.Mieszany;
		tmpTeamTactic.teamRecQual = enumRecQual.Mieszany;
		tmpTeamTactic.teamRecArea = enumRecArea.Normalny;
		tmpTeamTactic.teamBlock3 = enumBlock3.Mieszany;
		tmpTeamTactic.teamBlockOp = enumBlockOpt.Mieszany;
		tmpTeamTactic.teamRozStyl = enumRozStyle.Mieszany;
		tmpTeamTactic.teamRozStyl = enumRozStyle.Mieszany;
		
		tmpTeamTactic.atAtackStr = enumAtackStr.Mieszany;
		tmpTeamTactic.atAtackOri = enumAtackOri.Mieszany;
		tmpTeamTactic.atSerwStr = enumSerwStr.Mieszany;
		tmpTeamTactic.atSerwOri = enumSerwOri.Mieszany;
		tmpTeamTactic.atBlock3 = enumBlock3.Mieszany;
		tmpTeamTactic.atBlockOp = enumBlockOpt.Mieszany;
	
		tmpTeamTactic.rec1AtackStr = enumAtackStr.Mieszany;
		tmpTeamTactic.rec1AtackOri = enumAtackOri.Mieszany;
		tmpTeamTactic.rec1SerwStr = enumSerwStr.Mieszany;
		tmpTeamTactic.rec1SerwOri = enumSerwOri.Mieszany;
		tmpTeamTactic.rec1RecQual = enumRecQual.Mieszany;
		tmpTeamTactic.rec1RecArea = enumRecArea.Normalny;
		tmpTeamTactic.rec1Block3 = enumBlock3.Mieszany;
		tmpTeamTactic.rec1BlockOp = enumBlockOpt.Mieszany;
		
		tmpTeamTactic.rec2AtackStr = enumAtackStr.Mieszany;
		tmpTeamTactic.rec2AtackOri = enumAtackOri.Mieszany;
		tmpTeamTactic.rec2SerwStr = enumSerwStr.Mieszany;
		tmpTeamTactic.rec2SerwOri = enumSerwOri.Mieszany;
		tmpTeamTactic.rec2RecQual = enumRecQual.Mieszany;
		tmpTeamTactic.rec2RecArea = enumRecArea.Normalny;
		tmpTeamTactic.rec2Block3 = enumBlock3.Mieszany;
		tmpTeamTactic.rec2BlockOp = enumBlockOpt.Mieszany;
		
		tmpTeamTactic.block1AtackStr = enumAtackStr.Mieszany;
		tmpTeamTactic.block1AtackOri = enumAtackOri.Mieszany;
		tmpTeamTactic.block1SerwStr = enumSerwStr.Mieszany;
		tmpTeamTactic.block1SerwOri = enumSerwOri.Mieszany;
		tmpTeamTactic.block1Block3 = enumBlock3.Mieszany;
		tmpTeamTactic.block1BlockOp = enumBlockOpt.Mieszany;

		tmpTeamTactic.block2AtackStr = enumAtackStr.Mieszany;
		tmpTeamTactic.block2AtackOri = enumAtackOri.Mieszany;
		tmpTeamTactic.block2SerwStr = enumSerwStr.Mieszany;
		tmpTeamTactic.block2SerwOri = enumSerwOri.Mieszany;
		tmpTeamTactic.block2Block3 = enumBlock3.Mieszany;
		tmpTeamTactic.block2BlockOp = enumBlockOpt.Mieszany;
		
		tmpTeamTactic.rozSerwStr = enumSerwStr.Mieszany;
		tmpTeamTactic.rozSerwOri = enumSerwOri.Mieszany;
		tmpTeamTactic.rozBlock3 = enumBlock3.Mieszany;
		tmpTeamTactic.rozBlockOp = enumBlockOpt.Mieszany;
		tmpTeamTactic.rozRozStyl = enumRozStyle.Mieszany;
		tmpTeamTactic.rozRozStyl = enumRozStyle.Mieszany;
		
		tmpTeamTactic.libRecQual = enumRecQual.Mieszany;
		tmpTeamTactic.libRecArea = enumRecArea.Normalny;
		
		return tmpTeamTactic;
	}
}
