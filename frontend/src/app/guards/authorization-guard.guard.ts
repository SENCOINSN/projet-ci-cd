import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateFn,
  GuardResult,
  MaybeAsync, Router,
  RouterStateSnapshot
} from '@angular/router';
import {Injectable} from "@angular/core";
import {ServiceService} from "../service/service.service";

@Injectable()
export class AuthorizationGuardGuard implements CanActivate{
  constructor(private service:ServiceService,private router:Router) {
  }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    if(this.service.isAuthenticated){
      let requiredRoles= route.data['roles'];
      let userRoles=this.service.roles;
      for(let role of userRoles){
        if(requiredRoles.includes(role)){
          return true;
        }
      }
      return false;
    }else{
      this.router.navigateByUrl('/login')
      return false;
    }
  }

}
