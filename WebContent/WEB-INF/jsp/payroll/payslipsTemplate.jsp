<%@page import="org.spa.model.payroll.StaffPayroll"%>
<%@page import="org.spa.utils.DateUtil"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<style>
   h2{text-transform:uppercase;text-align:center}
   tr.payslips_first td {
        height: 25px;
        padding: 5px 0;
    }
     .hr01{border-top:2px solid #666;}
   .hr02{border-top:4px double #666;} 
  .staff_title{width:80%;float:left;max-height: 360px;}
  .staff_title ul {padding:0;margin:0;}
  .staff_title ul li{padding:0;margin:0;line-height:30px; list-style:outside;font-size:22px; margin-bottom: 15px}
   .staff_title ul li:last-child {margin-bottom: 0}
   .first_pay {font-weight:600;text-transform:uppercase;}
   .signed_list{width:99%;margin:0 auto;}
   .signed_list ul{margin:0;padding:0;}
   .signed_list ul li{padding:10px 0;margin-right:15px;line-height:20px; list-style:none;font-size:16px;float:left;width:47%; border-bottom:1px solid #666;}
   .remarks{ width:98%;border:1px solid #666;height:140px;padding:10px;font-weight:600;}
    .pdf_footer{width: 100%;
    position: fixed;
    z-index: 99;
    bottom: 0;
}
   .logo_company {position: fixed;
    z-index: 9;
    bottom: 4%;
    width: 50%;
    right: -14%;}
   
</style>
<h2><spring:message code="label.payroll.payslip"/> </h2>
<div style="width:100%;float:left;">
  <table width="100%">
    <tr class="payslips_first">
        <td>
            <table width="100%">
                <!-- BASIC DETAILS Start -->
                <tr class="payslips_first" colspan="4">
                  <td width="59%">
                    <div class="staff_title">
                      <ul>
                        <li><spring:message code="label.payroll.staff.name"/> : ${payroll.staff.fullName} </li>
                        <li><spring:message code="label.payroll.position"/> : ${payroll.staff.firstRoleForStaff.name} </li>
                        <li><spring:message code="label.sales.location"/> :
                          <c:forEach items="${payroll.staff.staffHomeShops}" var="s" varStatus="idx">
                            ${s.name}<c:if test="${!idx.last}"></c:if><c:if test="${!idx.last}">;</c:if>
                          </c:forEach>
                        </li>
                        <li><spring:message code="label.payroll.date"/> :
                        <%
                        StaffPayroll sp = (StaffPayroll)request.getAttribute("payroll");
                        String payrollDate=sp.getPayrollDate();
                		String[] strs =payrollDate.split("-");
                		String slDate=strs[0] +"-"+strs[1]+"-01";
                		Date date=DateUtil.stringToDate(slDate, "yyyy-MM-dd");
                		pageContext.setAttribute("date", date);
                        %><fmt:formatDate value="${date}" pattern="MMM yyyy"/></li>
                      </ul>
                     </div>
                  </td>
                  <td width="18%">&nbsp;</td>
                  <td colspan="2">&nbsp;</td>
                </tr>
               <tr>
                    <td colspan="4">
                        <hr class="hr01">
                    </td>
                </tr>
                <!-- BASIC DETAILS End -->
                <!-- BASIC STANDAR SALARY Start-->
                <tr class="payslips_second">
                    <td><span class="first_pay"><spring:message code="label.payroll.basic.salary.of.the.month"/> </span></td>
                    <td>&nbsp;</td>
                    <td width="14%" align="right">
                       <span class="first_pay"><spring:message code="label.money.currency"/> </span></td>
                    <td width="9%" align="right"><span class="first_pay"><font style="font-size:16px;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${payroll.standardSalary}"/></font></span></td>
                </tr>
                <!-- BASIC STANDAR SALARY End-->
                <!-- BASIC COMMISSION Start-->
                <tr class="payslips_second totalCommission">
                    <td><span class="first_pay"><spring:message code="label.total.commission"/> </span></td>
                    <td>&nbsp;</td>
                    <td align="right">
                        <span class="first_pay"><spring:message code="label.money.currency"/></span></td>
                    <td align="right"><span class="first_pay"><font style="font-size:16px;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${payroll.totalCommission}"/></font></span></td>
                </tr>  
                <c:forEach items="${payroll.staffPayrollCategoryStatisticses}" var="spcs">
                    <tr class="payslips_third commission">
                        <td>- Total ${spcs.bonusType} </td>
                        <td align="left"> <spring:message code="label.money.currency"/>
                            <span style="text-decoration:underline"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${spcs.salesStatistics}"/></span>
                        </td>
                        <td align="left">
                          <span style="text-decoration:underline"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${spcs.commissionStatistics}"/></span>
                         </td>
                        <td align="right">&nbsp;</td>
                    </tr>
                </c:forEach>
                
                <!-- BASIC COMMISSION End-->
                <!-- BASIC BONUS Start-->
                 
                <%-- <tr class="payslips_second">
                    <td colspan="2"><span class="first_pay">Bonus</span></td>
                      <td align="right">HKD$</td>
                    <td align="right">
                       <font style="font-size:16px;"> <fmt:formatNumber type="number" pattern="#,#00.0#" value="${payroll.totalBonus}"/> </font></td>
                
                </tr> --%>
                <%-- <c:forEach items="${item.staffPayrollCategoryStatisticses}" var="spcs">
                    <tr class="payslips_third">
                        <td>- Total ${spcs.bonusType}</td>
                        <td> HKD$
                        <span style="text-decoration:underline"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${spcs.salesStatistics}"/></span></td>
                        <td>
                            </td>
                            <td align="right">&nbsp;</td>
                    </tr>
                </c:forEach> --%>
                <!-- BASIC BONUS End-->
                <!-- BASIC TOTAL ADDITIONAL BEFORE MPF Start-->
                
                <tr class="payslips_second">
                    <td><span class="first_pay"><spring:message code="label.payroll.total.additional.deduction.before.mpf"/> </span></td>
                    <td>&nbsp;</td>
                      <td align="right"><span class="first_pay"><spring:message code="label.money.currency"/></span></td>
                    <td align="right">
                    <span class="first_pay"><font style="font-size:16px;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${payroll.totalAdditionalBfMpf}"/></font></span></td>
                    
                </tr>
                <c:forEach items="${payroll.additionalsBfMpf}" var="spa">
                    <tr class="payslips_third">
                        <td>- <spring:message code="label.total"/> ${spa.label}</td>
                        <td align="left"><spring:message code="label.money.currency"/><font style=""><fmt:formatNumber type="number" pattern="#,#00.0#" value="${spa.value}"/></font></td>
                        <td align="left">&nbsp;
                            
                        </td>
                        <td align="right">&nbsp;</td>
                    </tr>
                </c:forEach>
            
                <!-- BASIC TOTAL ADDITIONAL BEFORE MPF End-->
                <!-- BASIC LEAVE PAYS Start-->
               <%--  <tr class="payslips_second">
                    <td colspan="2"><span class="first_pay">No paid leave</span></td> 
                      <td align="right">HKD$</td>
                    <td align="right"><font style="font-size:16px;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${payroll.leavePays}"/></font></td>
                </tr> --%>
                <!-- BASIC LEAVE PAYS End-->
               
                <tr class="payslips_second">
                    <td><span class="first_pay"><spring:message code="label.payroll.total.earnings"/> </span></td>
                    <td>&nbsp;</td>
                    <td align="right"><span class="first_pay"><spring:message code="label.money.currency"/></span></td>
                    <td align="right">
                    <span class="first_pay"><font style="font-size:16px;"> <fmt:formatNumber type="number" pattern="#,#00.0#" value="${payroll.finalSalaryBeforeContribution}"/> </font></span></td>
                    
                </tr>
                  
                <tr class="payslips_second">
                    <td><span class="first_pay"><spring:message code="label.payroll.is.guaranteed.minimum"/> </span></td>
                    <td>&nbsp;</td>
                    <td colspan="2" align="right"><input type="hidden" id="isGM" value="${payroll.isUsedGm}"/><c:choose>
                      
                        <c:when test="${payroll.isUsedGm !=null && payroll.isUsedGm}"><span class="first_pay">Y</span> (<fmt:formatNumber type="number" pattern="#,#00.0#" value="${payroll.guaranteedMinimum}"/>)
                          <!-- <script type="text/javascript">
                  $(document).ready(function () {
                    var isGM = $('#isGM').val();
                    if(isGM){
                      $('.totalCommission').addClass('form-group-inactive');
                      $('.commission').addClass('form-group-inactive');
                    }
                  });
                </script> -->
                        </c:when>
                        <c:otherwise><span class="first_pay">N</span></c:otherwise>
                    </c:choose></td>
                </tr>
                <!-- BASIC CONTRIBUTION Start-->
                
                <tr class="payslips_second">
                    <td><span class="first_pay"><spring:message code="label.payroll.mpf.contribution"/> </span></td>
                    <td>&nbsp;</td>
                    <td align="right">
                       <span class="first_pay"><spring:message code="label.money.currency"/> </span></td>
                    <td align="right"><span class="first_pay"><font style="font-size:16px;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${payroll.contribution}"/></font></span></td>
                </tr>
                
                <!-- BASIC CONTRIBUTION End-->
                <!-- BASIC TOTAL ADDITIONAL BEFORE MPF Start-->
                <tr class="payslips_second">
                    <td><span class="first_pay"><spring:message code="label.payroll.total.additional.deduction.after.mpf"/> </span></td>
                    <td>&nbsp;</td>
                    <td align="right">
                       <span class="first_pay"> <spring:message code="label.money.currency"/></span></td>
                    <td align="right"><span class="first_pay"><font style="font-size:16px;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${payroll.totalAdditionalAfMpf}"/></font></span></td>
                </tr>
                <c:forEach items="${payroll.additionalsAfMpf}" var="spa">
                    <tr class="payslips_third">
                        <td>- Total ${spa.label}</td>
                        <td align="left">HKD$<font style=""><fmt:formatNumber type="number" pattern="#,#00.0#" value="${spa.value}"/></font></td>
                        <td align="left">
                            
                        </td>
                        <td align="right"></td>
                    </tr>
                </c:forEach>
                  <tr>
                    <td colspan="4">
                        <hr class="hr02">
                    </td>
                </tr>
                <!-- BASIC TOTAL ADDITIONAL AFTER MPF End-->
                <tr class="payslips_second">
                    <td> <span class="first_pay" style="font-size:22px;"><spring:message code="label.payroll.total.salary.for.the.month"/> </span></td>
                    <td>&nbsp;</td>
                    <td align="right" style="font-size:22px;">
                        <span class="first_pay"><spring:message code="label.money.currency"/></span></td>
                    <td align="right"><font class="first_pay" style="font-size:22px;"><fmt:formatNumber type="number" pattern="#,#00.0#" value="${payroll.finalSalary}"/></font></td>
                </tr>
                 <tr>
                    <td colspan="4">
                        <hr class="hr02">
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</div>
<div class="pdf_footer">
<div class="logo_company"><img src="/resources/img/company_chop.png" width="100" height="100" /></div>
 <table width="100%">
     <tr class="payslips_first">
                    <td colspan="4"><spring:message code="label.payroll.confirmation.of.salary.receipt"/> :</td>
                </tr>
                <tr class="payslips_first">
                    <td colspan="4">
                      <spring:message code="label.payroll.message"/>
                    </td>
                </tr>
          <%-- <tr class="payslips_first">
                    <td colspan="4"><div class="remarks">Remarks:<br/>${payroll.remarks }</div></td>
                </tr> --%>
                <tr class="payslips_fourth">
                  <td colspan="4">
                  <div class="signed_list">
                     <ul>
                        <li><spring:message code="label.payroll.signed"/> :</li>
                      <li><spring:message code="label.payroll.company.chop"/> : </li>
                      <li><spring:message code="label.payroll.employee"/> :&nbsp;&nbsp;&nbsp;${payroll.staff.fullName}</li>
                      <li><spring:message code="label.payroll.employer"/> :&nbsp;&nbsp;&nbsp;ASIA SPA AND WELLNESS LIMITED</li>
                      <li><spring:message code="label.date"/> :</li>
                      <li><spring:message code="label.date"/>:</li>
                     </ul>
                  </div> 
                  </td>
                </tr>
 </table>
</div>