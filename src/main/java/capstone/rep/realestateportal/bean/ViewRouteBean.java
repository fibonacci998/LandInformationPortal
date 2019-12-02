/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.rep.realestateportal.bean;

import capstone.rep.realestateportal.entity.Route;
import capstone.rep.realestateportal.service.CommonService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Phong
 */
@ManagedBean
@RequestScoped
public class ViewRouteBean {
    private String routeName;
    private String areaNearName;
    
    @PostConstruct
    public void init(){
        
    }
    
    public List<Route> getRouteByHint(String hint){
        String hintLowerCase = hint.toLowerCase();
        CommonService commonService = new CommonService();
        List<Route> listRouteByHint = commonService.getRouteByHint(hintLowerCase);
        return listRouteByHint;
    }
}
