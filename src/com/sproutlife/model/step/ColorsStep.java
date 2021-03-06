/*******************************************************************************
 * Copyright (c) 2016 Alex Shapiro - github.com/shpralex
 * This program and the accompanying materials
 * are made available under the terms of the The MIT License (MIT)
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *******************************************************************************/
package com.sproutlife.model.step;

import java.util.ArrayList;
import java.util.Collections;

import com.sproutlife.model.GameModel;
import com.sproutlife.model.echosystem.Organism;

public class ColorsStep extends Step {
    GameModel gameModel;   
       
    public ColorsStep(GameModel gameModel) {
        super(gameModel);
    }
        
    public void perform() {
    	    	
    	splitColors();
    }
    
    private void splitColors() {
        if (getEchosystem().getOrganisms().size() ==0) {
            return;
        }
        int kindCount[] = new int[3];
        for (Organism o : getEchosystem().getOrganisms()) {
            kindCount[o.getAttributes().kind]++;
            /*
            if (o.getKind()!=oneKind) {
                isOneKind=false;
            } 
            */           
        }

        if (kindCount[0]==0 || kindCount[1]==0 || kindCount[2]==0) {     
            int splitKind = -1;
            int emptyKind = -1;
            
            for (int i=0;i<3;i++) {
                if (kindCount[i]==0) {
                    emptyKind = i;
                }
                if (kindCount[i]*100/getEchosystem().getOrganisms().size()>70) {
                    splitKind = i;                
                }                
            }              
            
            if (splitKind!=-1) {
                /*
                ArrayList<Integer> lifespans = new ArrayList<Integer>();
                for (Organism o : getEchosystem().getOrganisms()) {                    
                    if (o.getKind()==splitKind) {
                        lifespans.add(getMutationCount(o));
                    }                                        
                }
                Collections.sort( lifespans);
                
                int middleL = 0;
                if (lifespans.size()>1) {                       
                    middleL = lifespans.get(lifespans.size()/2);
                }
                for (Organism o : getEchosystem().getOrganisms()) {
                    if (o.getKind()==splitKind) {                        
                        o.getKind() = (getMutationCount(o)<=middleL)?splitKind : emptyKind ;                                                                           
                    }
                }
                */
                
                ArrayList<Integer> xCoords = new ArrayList<Integer>();
                for (Organism o : getEchosystem().getOrganisms()) {                    
                    if (o.getAttributes().kind==splitKind) {
                        xCoords.add(o.x);
                    }                                        
                }
                Collections.sort( xCoords);
                
                int middleX = 0;
                if (xCoords.size()>1) {                       
                    middleX = xCoords.get(xCoords.size()/2);
                }
                for (Organism o : getEchosystem().getOrganisms()) {
                    if (o.getAttributes().kind==splitKind) {
                        if (middleX>getBoard().getWidth()/2) {
                            o.getAttributes().kind = o.x<middleX?splitKind : emptyKind;                            
                        }
                        else {
                            o.getAttributes().kind = o.x>middleX?splitKind : emptyKind;
                        }                       
                    }
                }
                
            }        
        }
    }
    
    private int getMutationCount(Organism o) {
        return o.getGenome().getRecentMutations(getEchosystem().getTime()-1000,getEchosystem().getTime(),o.lifespan).size();
    }

}
