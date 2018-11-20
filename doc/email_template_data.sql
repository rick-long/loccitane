-- 2017-11-7 by jason email templat Data -
INSERT INTO loccitane.mkt_email_template (id,type, subject, content, document_id, company_id, is_active, created, created_by, last_updated, last_updated_by)
VALUES (7,'ONLINE_GIFT_VOUCHER', 'Online Gift Voucher', '<div class="content"><h1></h1></div>', '', null, 1,now(), 'admin', now(), 'admin');
-- 2017-11-20 by jason email templat Data -
INSERT INTO loccitane.mkt_email_template (id,type, subject, content, document_id, company_id, is_active, created, created_by, last_updated, last_updated_by)
VALUES (8,'SEND_FRIEND_ONLINE_GIFT_VOUCHER', 'Send  Friend Online Gift Voucher', '<div class="content"><h1></h1></div>', '', null, 1,now(), 'admin', now(), 'admin');

-- 2017-11-20 by jason email templat Data -
INSERT INTO loccitane.mkt_email_template (id,type, subject, content, document_id, company_id, is_active, created, created_by, last_updated, last_updated_by)
VALUES (9,'PICK_UP_ONLINE_GIFT_VOUCHER', 'Thank', '<div class="content"><h1></h1></div>', '', null, 1,now(), 'admin', now(), 'admin');



-- 2017-12-5 by jason email templat Data -
UPDATE  mkt_email_template SET subject ='Thank you for purchasing Sense of Touch Gift Voucher!' ,content='<div class="content">
    <TABLE width="650" cellspacing="1" cellpadding="1">
        <TR>
            <TD colspan="3">Dear ${user.firstName} </TD>

        </TR>
        <TR>
            <TD colspan="3">&nbsp;</TD>

        <TR>

            <TD colspan="3">
                Thank you for your purchase at Sense of Touch.Your voucher has been enclosed as an attchment to this email.
            </TD>
        </TR>
		<TR>
            <TD colspan="3">&nbsp;</TD>
        </TR>
		<TR>
            <TD colspan="3">Please find below the details of your voucher:</TD>
        </TR>
        <TR>
            <TD colspan="3">&nbsp;</TD>
        </TR>
        <TR>
            <TD width="200">Voucher:</TD>
            <TD width="450">${prepaid.name}</TD>
        </TR>
        <TR>
            <TD width="200">Reference:</TD>
            <TD width="450">${prepaid.reference}</TD>
        </TR>
        <TR>
            <TD width="200">Expiry Date:</TD>
            <TD width="450">${expiryDate}</TD>
        </TR>

        <TR>
            <TD colspan="3">&nbsp;</TD>
        </TR>
        <TR>
            <TD colspan="3">
               	Please print out this pick up note and bring it to the designated shop to collect the voucher. 
				Should you have any queries or make an appointment for your gift voucher, 
				please contact us at 2980 7698 (Cyberport), 2987 9198 (Discovery Bay), 2526 6918 (Central),
 3983 0406 (Tseung Kwan O), 2791 2278 (Sai Kung), 2592 9668 (Repulse Bay) or email us 
to enquiries@senseoftouch.com.hk and we will be happy to assist.
            </TD>
        </TR>
        <TR>
            <TD colspan="3">&nbsp;</TD>
        </TR>
        <TR>
            <TD colspan="3">
                Thank you and have a nice day.
            </TD>
        </TR>
        <TR>
            <TD colspan="3">&nbsp;</TD>
        </TR>
        <tr>
            <td colspan="3">
                Best Regards,
            </td>
        </tr>
        <TR>
            <TD colspan="3">
                Sense of Touch
            </TD>
        </TR>
        <TR>
            <TD colspan="3">&nbsp;</TD>
        </TR>
        <TR>
            <TD colspan="3">
                <b>Sense of Touch locations:</b>
            </TD>
        </TR>
        <TR>
            <TD>
                Central Spa:
            </TD>
            <td>
                52 D aguilar Street, Lan Kwai Fong, Central
            </td>
			<td>Tel: 2526 6918</td>
        </TR>
        <TR>
            <TD>
                Repulse Bay Spa:
            </TD>
            <td>
                G211, 1/F, The Repulse Bay Arcade, 109 Repulse Bay Road, HK
            </td>
			<td>Tel: 2592 9668</td>
        </TR>
		<TR>
            <TD>
                Cyberport Spa:
            </TD>
            <td>
                3/F, Le Meridien Cyberport, 100 Cyberport Road, Pok Fu Lam, HK
            </td>
			<td>Tel: 2980 7698</td>
        </TR>
        <TR>
            <TD>
                Discovery Bay Spa:
            </TD>
            <td>
                140A-1, 1/F, Block C, DB Plaza, Discovery Bay, Lantau Island
            </td>
			<td>Tel: 2987 9198</td>
        </TR>
        <TR>
            <TD>
                Tseung Kwan O Spa:
            </TD>
            <td>
                Crowne Plaza Hong Kong Kowloon East 3/F,3 Tong Tak Street, Tseung Kwan O, HK
            </td>
			<td>Tel: 3983 0406</td>
        </TR>
        <TR>
            <TD>
                Sai Kung Spa:
            </TD>
            <td>
                G/F, 77 Man Nin Street, Sai Kung, New Territories
            </td>
			<td>Tel: 2791 2278</td>
        </TR>
		<TR>
            <TD>
                Tung Chung</br>
				Visionnaire Club Spa:
            </TD>
            <td>
                2/F Visionnaire Club, The Visionary, 1 Ying Hong Street, 
				</br>Tung Chung, HK (*This location is exclusive for residents of The 
				</br>Visionary and their invited guests)
            </td>
            <td>Tel: 2561 6832</td>
        </TR>
		<TR>
            <TD colspan="3">
                <b>Capelli Hair Salon location:</b>
            </TD>
        </TR>
        <TR>
            <TD>
                Capelli Hair Salon:
            </TD>
            <td>
                G211, 1/F, The Repulse Bay Arcade,109 Repulse Bay Road
            </td>
			<td>Tel: 2592 9559</td>
        </TR>

    </TABLE>
	</div>'
WHERE type='ONLINE_GIFT_VOUCHER';


UPDATE  mkt_email_template SET subject ='A Pampering Gift for You!' ,content='<div class="content"> <TABLE width="650" cellspacing="1" cellpadding="1">
               <TR>
                   <TD colspan="3">Dear ${prepaid.additionalName}</TD>
               </TR>
               <TR>
                   <TD colspan="3">&nbsp;</TD>
               </TR>

               <TR>
                   <TD colspan="3">
                       Your friend ${user.fullName} has bought you a gift of ${prepaid.name} for your indulgence at Sense of Touch! Your voucher has been enclosed as an attachment to this email.<br/><br/>
                       Please find below the details of your voucher:
                   </TD>
               </TR>
               <TR>
                   <TD colspan="3">&nbsp;</TD>
               </TR>
               <TR>
                   <TD width="200">Voucher:</TD>
                   <TD width="450">${prepaid.name}</TD>
               </TR>
               <TR>
                   <TD width="200">Reference:</TD>
                   <TD width="450">${prepaid.reference}</TD>
               </TR>
               <TR>
                   <TD width="200">Expiry Date:</TD>
                   <TD width="450">${expiryDate}</TD>
               </TR>

                   <TR>
                       <TD colspan="3">Your friend also sent along the following message:</TD>
                   </TR>
                   <TR>
                       <TD colspan="3">${prepaid.additionalMessage}</TD>
                   </TR>
                   <TR>
                       <TD colspan="3">&nbsp;</TD>
                   </TR>
               <TR>
		            <TD colspan="3">
				Should you have any queries or make an appointment for your gift voucher, 
				please contact us at 2980 7698 (Cyberport), 2987 9198 (Discovery Bay), 2526 6918 (Central),
 3983 0406 (Tseung Kwan O), 2791 2278 (Sai Kung), 2592 9668 (Repulse Bay) or email us 
to enquiries@senseoftouch.com.hk and we will be happy to assist.
            </TD>
		       </TR>
               <TR>
                   <TD colspan="3">&nbsp;</TD>
               </TR>
               <tr>
                   <TD colspan="3">
                       Thank you and have a nice day.
                   </td>
               </tr>

               <TR>
                   <TD colspan="3">&nbsp;</TD>
               </TR>
               <tr>
                   <TD colspan="3">
                       Best Regards,
                   </td>
               </tr>
               <TR>
                   <TD colspan="3">
                       Sense of Touch
                   </TD>
               </TR>
				<TR>
                   <TD colspan="3">&nbsp;</TD>
               </TR>

        <TR>
            <TD colspan="3">
                <b>Sense of Touch locations:</b>
            </TD>
        </TR>
        <TR>
            <TD>
                Central Spa:
            </TD>
            <td>
                52 D aguilar Street, Lan Kwai Fong, Central
            </td>
			<td>Tel: 2526 6918</td>
        </TR>
        <TR>
            <TD>
                Repulse Bay Spa:
            </TD>
            <td>
                G211, 1/F, The Repulse Bay Arcade, 109 Repulse Bay Road, HK
            </td>
			<td>Tel: 2592 9668</td>
        </TR>
		<TR>
            <TD>
                Cyberport Spa:
            </TD>
            <td>
                3/F, Le Meridien Cyberport, 100 Cyberport Road, Pok Fu Lam, HK
            </td>
			<td>Tel: 2980 7698</td>
        </TR>
        <TR>
            <TD>
                Discovery Bay Spa:
            </TD>
            <td>
                140A-1, 1/F, Block C, DB Plaza, Discovery Bay, Lantau Island
            </td>
			<td>Tel: 2987 9198</td>
        </TR>
        <TR>
            <TD>
                Tseung Kwan O Spa:
            </TD>
            <td>
                Crowne Plaza Hong Kong Kowloon East 3/F,3 Tong Tak Street, Tseung Kwan O, HK
            </td>
			<td>Tel: 3983 0406</td>
        </TR>
        <TR>
            <TD>
                Sai Kung Spa:
            </TD>
            <td>
                G/F, 77 Man Nin Street, Sai Kung, New Territories
            </td>
			<td>Tel: 2791 2278</td>
        </TR>
		<TR>
            <TD>
                Tung Chung</br>
				Visionnaire Club Spa:
            </TD>
            <td>
                2/F Visionnaire Club, The Visionary, 1 Ying Hong Street, 
				</br>Tung Chung, HK (*This location is exclusive for residents of The 
				</br>Visionary and their invited guests)
            </td>
            <td>Tel: 2561 6832</td>
        </TR>
		<TR>
            <TD colspan="3">
                <b>Capelli Hair Salon location:</b>
            </TD>
        </TR>
        <TR>
            <TD>
                Capelli Hair Salon:
            </TD>
            <td>
                G211, 1/F, The Repulse Bay Arcade,109 Repulse Bay Road
            </td>
			<td>Tel: 2592 9559</td>
        </TR>
           </TABLE>
   	</div>'
WHERE type='SEND_FRIEND_ONLINE_GIFT_VOUCHER';


UPDATE  mkt_email_template SET subject ='Thank you for purchasing Sense of Touch Gift Voucher!' ,content='<div class="content">
    <TABLE width="650" cellspacing="1" cellpadding="1">
        <TR>
            <TD colspan="3">Dear ${user.firstName} </TD>

        </TR>
        <TR>
            <TD colspan="3">&nbsp;</TD>

        <TR>
			<TD colspan="3">
                Thank you for your purchase at Sense of Touch.Your voucher has been enclosed as an attchment to this email.
            </TD>
        </TR>
        <TR>
            <TD colspan="3">&nbsp;</TD>
        </TR>
        <TR>
            <TD width="200">Voucher:</TD>
            <TD width="450">${prepaid.name}</TD>
        </TR>
        <TR>
            <TD width="200">Reference:</TD>
            <TD width="450">${prepaid.reference}</TD>
        </TR>
        <TR>
            <TD width="200">Expiry Date:</TD>
            <TD width="450">${expiryDate}</TD>
        </TR>
		<TR>
		    <TD width="200">Pick up shop:</TD>
		    <TD width="450">${prepaid.pickUpLocation}</TD>
		</TR>
        <TR>
            <TD colspan="3">&nbsp;</TD>
        </TR>
        <TR>
           <TD colspan="3">
               	Please print out this pick up note and bring it to the designated shop to collect the voucher. 
				Should you have any queries or make an appointment for your gift voucher, 
				please contact us at 2980 7698 (Cyberport), 2987 9198 (Discovery Bay), 2526 6918 (Central),
 3983 0406 (Tseung Kwan O), 2791 2278 (Sai Kung), 2592 9668 (Repulse Bay) or email us 
to enquiries@senseoftouch.com.hk and we will be happy to assist.
            </TD>
        </TR>
        <TR>
            <TD colspan="3">&nbsp;</TD>
        </TR>
        <TR>
            <TD colspan="3">
                Thank you and have a nice day.
            </TD>
        </TR>
        <TR>
            <TD colspan="3">&nbsp;</TD>
        </TR>
        <tr>
            <TD colspan="3">
                Best Regards,
            </td>
        </tr>
        <TR>
            <TD colspan="3">
                Sense of Touch
            </TD>
        </TR>
        <TR>
            <TD colspan="3">&nbsp;</TD>
        </TR>
        <TR>
            <TD colspan="3">
                <b>Sense of Touch locations:</b>
            </TD>
        </TR>
        <TR>
            <TD>
                Central Spa:
            </TD>
            <td>
                52 D aguilar Street, Lan Kwai Fong, Central
            </td>
			<td>Tel: 2526 6918</td>
        </TR>
        <TR>
            <TD>
                Repulse Bay Spa:
            </TD>
            <td>
                G211, 1/F, The Repulse Bay Arcade, 109 Repulse Bay Road, HK
            </td>
			<td>Tel: 2592 9668</td>
        </TR>
		<TR>
            <TD>
                Cyberport Spa:
            </TD>
            <td>
                3/F, Le Meridien Cyberport, 100 Cyberport Road, Pok Fu Lam, HK
            </td>
			<td>Tel: 2980 7698</td>
        </TR>
        <TR>
            <TD>
                Discovery Bay Spa:
            </TD>
            <td>
                140A-1, 1/F, Block C, DB Plaza, Discovery Bay, Lantau Island
            </td>
			<td>Tel: 2987 9198</td>
        </TR>
        <TR>
            <TD>
                Tseung Kwan O Spa:
            </TD>
            <td>
                Crowne Plaza Hong Kong Kowloon East 3/F,3 Tong Tak Street, Tseung Kwan O, HK
            </td>
			<td>Tel: 3983 0406</td>
        </TR>
        <TR>
            <TD>
                Sai Kung Spa:
            </TD>
            <td>
                G/F, 77 Man Nin Street, Sai Kung, New Territories
            </td>
			<td>Tel: 2791 2278</td>
        </TR>
		<TR>
            <TD>
                Tung Chung</br>
				Visionnaire Club Spa:
            </TD>
            <td>
                2/F Visionnaire Club, The Visionary, 1 Ying Hong Street, 
				</br>Tung Chung, HK (*This location is exclusive for residents of The 
				</br>Visionary and their invited guests)
            </td>
            <td>Tel: 2561 6832</td>
        </TR>
		<TR>
            <TD colspan="3">
                <b>Capelli Hair Salon location:</b>
            </TD>
        </TR>
        <TR>
            <TD>
                Capelli Hair Salon:
            </TD>
            <td>
                G211, 1/F, The Repulse Bay Arcade,109 Repulse Bay Road
            </td>
			<td>Tel: 2592 9559</td>
        </TR>

    </TABLE>
	</div>'
WHERE type='PICK_UP_ONLINE_GIFT_VOUCHER';

-- 2017-11-20 by jason email templat Data -
INSERT INTO loccitane.mkt_email_template (id,type, subject, content, document_id, company_id, is_active, created, created_by, last_updated, last_updated_by)
VALUES (10,'CONFIRM_ONLINE_GIFT_VOUCHER', 'Thank you for purchasing Sense of Touch Gift Voucher!', '<div class="content"> <TABLE width="650" cellspacing="1" cellpadding="1">
               <TR>
                   <TD colspan="3">Dear ${user.fullName}</TD>
               </TR>
               <TR>
                   <TD colspan="3">&nbsp;</TD>
               </TR>

               <TR>
                   <TD colspan="3">
                      Thank you for your purchase at Sense of Touch. The gift voucher has been send to the email address of :${prepaid.additionalEmail}
                   </TD>
               </TR>
               <TR>
                   <TD colspan="3">&nbsp;</TD>
               </TR>
               <TR>
                   <TD width="200">Voucher:</TD>
                   <TD width="450">${prepaid.name}</TD>
               </TR>
               <TR>
                   <TD width="200">Reference:</TD>
                   <TD width="450">${prepaid.reference}</TD>
               </TR>
               <TR>
                   <TD width="200">Expiry Date:</TD>
                   <TD width="450">${expiryDate}</TD>
               </TR>
                   <TR>
                       <TD colspan="3">&nbsp;</TD>
                   </TR>
               <TR>
		            <TD colspan="3">
				Should you have any queries or make an appointment for your gift voucher, 
				please contact us at 2980 7698 (Cyberport), 2987 9198 (Discovery Bay), 2526 6918 (Central),
 3983 0406 (Tseung Kwan O), 2791 2278 (Sai Kung), 2592 9668 (Repulse Bay) or email us 
to enquiries@senseoftouch.com.hk and we will be happy to assist.
            </TD>
		       </TR>
               <TR>
                   <TD colspan="3">&nbsp;</TD>
               </TR>
               <tr>
                   <TD colspan="3">
                       Thank you and have a nice day.
                   </td>
               </tr>

               <TR>
                   <TD colspan="3">&nbsp;</TD>
               </TR>
               <tr>
                   <TD colspan="3">
                       Best Regards,
                   </td>
               </tr>
               <TR>
                   <TD colspan="3">
                       Sense of Touch
                   </TD>
               </TR>
				<TR>
                   <TD colspan="3">&nbsp;</TD>
               </TR>

        <TR>
            <TD colspan="3">
                <b>Sense of Touch locations:</b>
            </TD>
        </TR>
        <TR>
            <TD>
                Central Spa:
            </TD>
            <td>
                52 D aguilar Street, Lan Kwai Fong, Central
            </td>
			<td>Tel: 2526 6918</td>
        </TR>
        <TR>
            <TD>
                Repulse Bay Spa:
            </TD>
            <td>
                G211, 1/F, The Repulse Bay Arcade, 109 Repulse Bay Road, HK
            </td>
			<td>Tel: 2592 9668</td>
        </TR>
		<TR>
            <TD>
                Cyberport Spa:
            </TD>
            <td>
                3/F, Le Meridien Cyberport, 100 Cyberport Road, Pok Fu Lam, HK
            </td>
			<td>Tel: 2980 7698</td>
        </TR>
        <TR>
            <TD>
                Discovery Bay Spa:
            </TD>
            <td>
                140A-1, 1/F, Block C, DB Plaza, Discovery Bay, Lantau Island
            </td>
			<td>Tel: 2987 9198</td>
        </TR>
        <TR>
            <TD>
                Tseung Kwan O Spa:
            </TD>
            <td>
                Crowne Plaza Hong Kong Kowloon East 3/F,3 Tong Tak Street, Tseung Kwan O, HK
            </td>
			<td>Tel: 3983 0406</td>
        </TR>
        <TR>
            <TD>
                Sai Kung Spa:
            </TD>
            <td>
                G/F, 77 Man Nin Street, Sai Kung, New Territories
            </td>
			<td>Tel: 2791 2278</td>
        </TR>
		<TR>
            <TD>
                Tung Chung</br>
				Visionnaire Club Spa:
            </TD>
            <td>
                2/F Visionnaire Club, The Visionary, 1 Ying Hong Street, 
				</br>Tung Chung, HK (*This location is exclusive for residents of The 
				</br>Visionary and their invited guests)
            </td>
            <td>Tel: 2561 6832</td>
        </TR>
		<TR>
            <TD colspan="3">
                <b>Capelli Hair Salon location:</b>
            </TD>
        </TR>
        <TR>
            <TD>
                Capelli Hair Salon:
            </TD>
            <td>
                G211, 1/F, The Repulse Bay Arcade,109 Repulse Bay Road
            </td>
			<td>Tel: 2592 9559</td>
        </TR>
           </TABLE>
   	</div>', '', null, 1,now(), 'admin', now(), 'admin');


   	-- 2017-12-11 by jason  -
UPDATE  mkt_email_template SET content='<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><br />
</span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span style="line-height:1.5;font-family:Verdana;">Hi ${user.fullName},</span><span class="Apple-converted-space"> <br />
</span></span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span class="Apple-converted-space"><br />
</span></span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span style="line-height:1.5;font-family:Verdana;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="line-height:1.5;font-family:Verdana;">We''ve received a request to reset your password. </span><br />
</span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span style="line-height:1.5;font-family:Verdana;">&nbsp;&nbsp;&nbsp;&nbsp;</span>If you didn''t make the request, just ignore this email.</span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span style="line-height:1.5;font-family:Verdana;">&nbsp;&nbsp;&nbsp;&nbsp;</span>Otherwise, you can reset your password using this link:</span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span style="line-height:1.5;font-family:Verdana;">&nbsp;&nbsp;&nbsp;&nbsp;</span><a href="${urlRoot}/resetPassword?code=${code}" target="_blank"><span style="line-height:1.5;font-family:Verdana;">Click here to reset your password</span></a></span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><br />
</span>
</p>
<p>
	<span style="color:#000000;font-family:Verdana;font-size:14px;font-style:normal;font-weight:normal;line-height:1.5;background-color:#FFFFFF;">Thanks, </span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span style="line-height:1.5;font-family:Verdana;">SOT</span><br />
</span>
</p>'
WHERE type='RESET_PASSWORD';


   	-- 2018-1-15 by jason  -
UPDATE  mkt_email_template SET content='<div class="content" style="width: 800px; margin: 0 auto;">

<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><br />
</span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span style="line-height:1.5;font-family:Verdana;">Dear ${user.firstName},</span><span class="Apple-converted-space"> <br />
</span></span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span class="Apple-converted-space"><br />
</span></span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span style="line-height:1.5;font-family:Verdana;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="line-height:1.5;font-family:Verdana;">Welcome to Sense of Touch !</span><br />
</span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span style="line-height:1.5;font-family:Verdana;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="line-height:1.5;font-family:Verdana;">You have been successfully registered.</span><span style="line-height:1.5;font-family:Verdana;" class="Apple-converted-space">&nbsp;</span></span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span style="line-height:1.5;font-family:Verdana;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="line-height:1.5;font-family:Verdana;">When you login to your account, you will be able to do the following:</span><br />
</span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span style="line-height:1.5;font-family:Verdana;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="line-height:1.5;font-family:Verdana;">- Purchase online gift vouchers for your family and friends</span><br />
</span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span style="line-height:1.5;font-family:Verdana;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="line-height:1.5;font-family:Verdana;">- Review the remaining credit/ units and expiry date from your package and voucher</span><br />
</span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span style="line-height:1.5;font-family:Verdana;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="line-height:1.5;font-family:Verdana;">- Make changes to your account information</span><br />
</span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span style="line-height:1.5;font-family:Verdana;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="line-height:1.5;font-family:Verdana;">- Change your password</span><br />
</span>
</p>
<p></p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><span style="line-height:1.5;font-family:Verdana;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="line-height:1.5;font-family:Verdana;">If you have any questions, please contact our spa concierge or our online customer service team at www.senseoftouch.com.hk or email enquiries@senseoftouch.com.hk</span><br />
</span>
</p>
<p>
	<span style="color:#000000;font-family:''lucida Grande'', Verdana, ''Microsoft YaHei'';font-size:14px;font-style:normal;font-weight:normal;line-height:23.8px;background-color:#FFFFFF;"><br />
</span>
</p>
<p>
	<span style="color:#000000;font-family:Verdana;font-size:14px;font-style:normal;font-weight:normal;line-height:1.5;background-color:#FFFFFF;">Thank you and have a nice day. </span>
</p>
<p>
	<span style="color:#000000;font-family:Verdana;font-size:14px;font-style:normal;font-weight:normal;line-height:1.5;background-color:#FFFFFF;">Best Regards, </span>
</p>
<p>
	<span style="color:#000000;font-family:Verdana;font-size:14px;font-style:normal;font-weight:normal;line-height:1.5;background-color:#FFFFFF;">Sense of Touch  </span>
</p>
<p>
</p>
</div>'
WHERE type='REGISTRATION';

-- 2018-1-18 by jason  -
INSERT INTO loccitane.mkt_email_template (type, subject, content, document_id, company_id, is_active, created, created_by, last_updated, last_updated_by) VALUES ('SEND_THANK_YOU_EMAIL', 'Send Thank You Email', '<table width="800" cellspacing="0" cellpadding="0" border="0" align="center">
                <tbody>
                <tr>
                    <td bgcolor="#ffffff">
                        <div style="box-shadow:0 0 15px rgba(0,0,0,0.1);padding: 20px 20px 20px 20px">
                            <TABLE width="100%" cellspacing="1" cellpadding="1" border="0">
                                <TR>
                                    <TD style="font-size: 14px;">Dear ${user.firstName}
                                    </TD>
                                </TR>
                                <TR>
                                    <TD> </TD>
                                </TR>
                                <TR>
                                    <TD>
                                       Thank You for purchaing:
                                    </TD>
                                </TR>

                                <TR>
                                <TR>
                                    <TD> http://www.baidu.com</TD>

                                <tr><tr>
                                    <td> </td>
                                </tr>
                                <TR>
                                    <TD>Thank you and have a nice day.</TD>

                                <tr>
                                <tr>
                                    <td><span style="text-align:left;">Best Regards, </span><br/>
                                        Sense of Touch
                                  </td>
                                </tr>
                            </TABLE>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td style="line-height:0"> </td>
                </tr>
</tbody></table>', '', null, 1, '2018-01-08 10:19:05', 'admin', '2018-01-08 10:19:14', 'admin');

-- 2018-1-24 by jason  -
INSERT INTO loccitane.mkt_email_template (type, subject, content, document_id, company_id, is_active, created, created_by, last_updated, last_updated_by) VALUES ('SEND_THANK_YOU_EMAIL', 'Send Thank You Email', '    <!--content-->
             <table width="800" cellspacing="0" cellpadding="0" border="0" align="center">
                   <tbody>
                   <tr>
                       <td bgcolor="#ffffff">
                           <div style="box-shadow:0 0 15px rgba(0,0,0,0.1);padding: 20px 20px 20px 20px">
                               <TABLE width="100%" cellspacing="1" cellpadding="1" border="0">
                                   <TR>
                                       <TD style="font-size: 14px;">Dear ${user.firstName}
                                       </TD>
                                   </TR>
                                   <TR>
                                       <TD> </TD>
                                   </TR>
                                   <TR>
                                       <TD>
                                         Thank you for your recent visit to our shop. We want to provide you with the best service possible.
                                       </TD>
                                   </TR>
									<TR>
                                       <TD>
                                         Would you consider posting a review of us online? In addition to providing feedback,online reviews can help other customers learn about who we are and about the services we offer. 
                                       </TD>
                                   </TR>
									<TR>
                                       <TD>
                                         They are also a great way to give referrals to your family and friends.
                                       </TD>
                                   </TR>
 									<tr>
                                       <td> </td>
                                   </tr>
									<TR>
                                       <TD>
                                        Plase take a minute to leave a comment on the following link - we would really appreciate it!
                                       </TD>
                                   </TR>
									<tr>
                                       <td> </td>
                                   </tr>
                                   <TR>
                                       <TD>
											<a href="${urlRoot}/reviewForm/${purchaseOrder.reference}">Click Here</a>
										</TD>
									</TR>
                                   <tr>
                                       <td> </td>
                                   </tr>
                                   <TR>
                                       <TD>Thank you, in advance, for your review patronage!</TD>

                                   <tr>
                               </TABLE>
                           </div>
                       </td>
                   </tr>
                   <tr>
                       <td style="line-height:0"> </td>
                   </tr>
 </tbody></table>', '', null, 1, '2018-01-08 10:19:05', 'admin', '2018-01-08 10:19:14', 'admin');
 
 
 
-- 2018-1-30 by Ivy  -
INSERT INTO loccitane.mkt_email_template (type, subject, content, document_id, company_id, is_active, created, created_by, last_updated, last_updated_by) VALUES ('DAY_END_REPORT', 'Day End Report', '    <!--content-->
             <table width="800" cellspacing="0" cellpadding="0" border="0" align="center">
                   <tbody>
                   <tr>
                       <td bgcolor="#ffffff">
                           <div style="box-shadow:0 0 15px rgba(0,0,0,0.1);padding: 20px 20px 20px 20px">
                               <TABLE width="100%" cellspacing="1" cellpadding="1" border="0">
                                   <TR>
                                       <TD style="font-size: 14px;">Dear Sir or Madam
                                       </TD>
                                   </TR>
                                   <TR>
                                       <TD> </TD>
                                   </TR>
                                   <TR>
                                       <TD>
                                        	Please find attached reports of ${shopName} for your reference.
                                       </TD>
                                   </TR>
                                   <tr>
                                       <td> </td>
                                   </tr>
                                   <TR>
                                       <TD>Best regards</TD>
									</TR>
                                   <tr>
 									<TR>
                                       <TD>Sense of Touch</TD>
									</TR>
                                   <tr>
                                       <td> </td>
                                   </tr>
                               </TABLE>
                           </div>
                       </td>
                   </tr>
                   <tr>
                       <td style="line-height:0"> </td>
                   </tr>
 </tbody></table>', '', null, 1, now(), 'admin', now(), 'admin');




-- 2018-2-5 by Ivy  -
INSERT INTO loccitane.mkt_email_template (type, subject, content, document_id, company_id, is_active, created, created_by, last_updated, last_updated_by) VALUES ('DAILY_REPORT', 'Daily Report For Shop Manager', '    <!--content--> <style type="text/css">
* {
			word-wrap: break-word;
			word-break: break-all;
		}

		a {
			color: #0000ff;
			text-decoration: none;
		}

		a:link {
			color: #0000ff;
		}

		a:visited {
			color: #0000ff;
		}

		a:active {
			color: #0000ff;
			text-decoration: underline;
		}

		a:hover {
			color: #009900;
			text-decoration: underline;
		}

		body {
			font-size: 12px;
			text-decoration: none;
			font-family: Arial, Helvetica, sans-serif;
			font-style: normal;
		}

		td {
			font-size: 12px;
			text-decoration: none;
			font-family: Arial, Helvetica, sans-serif;
			font-style: normal;
		}

		#details table {
			border-collapse: collapse;
		}

		#details td {
			border-width: 1px;
			border-style: solid;
			border-color: #cccccc;
			border-collapse: collapse;
			border-left: 0;
			border-right: 0
		}

@media only screen and (max-width: 480px)
{
.Daily_Total_Sales_div {
    width: 100%!important;
    margin: 0 auto;
    display: inline-block;
    vertical-align: top;
    word-wrap: break-word;
    table-layout: fixed;
    float: left!important;
}

.table {
    width: 100%;
    max-width: 100%;
    margin-bottom: 20px;
    margin: 0 auto;
}

table.template_table_content {
    width: 100%!important;
    padding: 10px;
}

#pageList {
    width:100%;
    background: #fff;
}

.table-responsive {
    min-height: .01%;
    overflow-x: auto;
    float: left;
    width: 100%;
}


table.social_media {
    width: 100%;
    float: left;
}

table.social_media tr td{width: 33%;
    float: left;}


table.footer_table {
    width: 100%;
    float: left;
}

table.footer_table tr td {
    padding: 3px 5px;
    word-wrap: break-word;
    word-break: break-all;
}

}

@media only screen and (max-width: 768px)
{
.Daily_Total_Sales_div {
    width: 49.7%;
    margin: 0 auto;
    display: inline-block;
    vertical-align: top;
    margin-bottom: 10px;
    float: left;
	margin-bottom: 10px;
}

.table {
    width: 100%;
    max-width: 100%;
    margin-bottom: 20px;
    margin: 0 auto;
}

table.template_table_content {
    width: 100%!important;
    padding: 10px;
}

#pageList {
    width:100%;
    background: #fff;
}

.table-responsive {
    min-height: .01%;
    overflow-x: auto;
    float: left;
    width: 100%;
}
}

@media only screen and (max-width: 1024px)
{
.Daily_Total_Sales_div {
    width: 49.7%;
    margin: 0 auto;
    display: inline-block;
    vertical-align: top;
	margin-bottom: 10px;
}

.table {
    width: 100%;
    max-width: 100%;
    margin-bottom: 20px;
    margin: 0 auto;
}

table.template_table_content {
    width: 100%!important;
    padding: 10px;
}

#pageList {
    width:100%;
    background: #fff;
}

.table-responsive {
    min-height: .01%;
    overflow-x: auto;
    float: left;
    width: 100%;
}

}

	</style>
		<table width="100%" cellspacing="0" cellpadding="0" border="0" align="center" class="template_table_content" style="max-width:800px;" role="presentation">
				<tbody>
				<tr>
					<td bgcolor="#ffffff">
						<div style="box-shadow:0 0 15px rgba(0,0,0,0.1);padding: 20px 20px 20px 20px">
               <table width="100%">
                  <tr>
                     <td>	<div id="pageList"><div class="table-responsive">
<table class="table report_inner_table" width="100%">
	<tbody><tr>
		<th style="color: #ffffff; background: #808080;font-size: 16px!important;text-align: left;padding:7px 10px;">A) Daily Total Sales</th>
	</tr>
	<tr valign="top">
		<td>
        <div class="Daily_Total_Sales_div" style="width: 49.5%;
    margin: 0 auto;
    display: inline-block;
    vertical-align: top;
    word-wrap: break-word;
    table-layout: fixed;"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="table report_inner_table table-striped" role="presentation">
  <tbody><tr>
    <td height="25"> <h5 style="width: 100%;background: none;color: #404040;margin: 0;padding: 0;font-weight: 600">Income Sub-total:&nbsp;${sumIncome}</h5></td>
</tr>
	<tr>
		<td height="25">
			-&nbsp;&nbsp;Cash:&nbsp;${cash}
		</td>
	</tr>
	<tr>
		<td height="25">
			-&nbsp;&nbsp;EPS:&nbsp;${eps}
		</td>
	</tr>
	<tr>
		<td height="25">
			-&nbsp;&nbsp;VISA:&nbsp;${visa}
		</td>
	</tr>
	<tr>
		<td height="25">
			-&nbsp;&nbsp;AE:&nbsp;${ae}
		</td>
	</tr>
	<tr>
		<td height="25">
			-&nbsp;&nbsp;Unionpay:&nbsp;${uniompay}
		</td>
	</tr>
	<tr>
		<td height="25">
			-&nbsp;&nbsp;Others:&nbsp;${others}
		</td>
	</tr>
</tbody></table>
</div>
  <div class="Daily_Total_Sales_div" style="width: 49.5%;
    margin: 0 auto;
    display: inline-block;
    vertical-align: top;
    word-wrap: break-word;
    table-layout: fixed;"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="table report_inner_table table-striped" align="right" role="presentation">
  <tbody><tr>
		<td height="25"><h5 style="width: 100%;background: none;color: #404040;margin: 0;padding: 0;font-weight: 600">Prepaid Sub-total:&nbsp;${sumPrepaid}</h5></td>
	</tr>
	<tr>
		<td height="25">
			-&nbsp;&nbsp;Package:&nbsp;${packages}
		</td>
	</tr>
	<tr>
		<td height="25">
			-&nbsp;&nbsp;Voucher:&nbsp;${voucher}
		</td>
	</tr>
	<tr>
		<td height="25">
			-&nbsp;&nbsp;Hotel Guest:&nbsp;${hotels}
		</td>
	</tr>
	<tr>
		<td height="25">
			-&nbsp;&nbsp;Wings II Guest:&nbsp;${wings}
		</td>
	</tr>
</tbody></table>
</div>
       </td>
	</tr>
	<tr>
		<td class="value_default total" style="border-top: 1px solid;
    padding: 10px 0!important;
    font-size: 14px!important;
    font-weight: bold!important;">Total Sales:&nbsp;${totalSales}</td>
	</tr>
</tbody></table>
</div>
<br>
<div class="table-responsive">
<table class="table report_inner_table table-striped" width="100%" role="presentation">
	<tbody><tr>
		<th style="color: #ffffff; background: #808080;font-size: 16px!important;text-align: left;padding:7px 10px;">B) Daily Breakdown</th>
	</tr>
	<tr>
		<td height="25">Number of Clients:&nbsp;${numOfClients}</td>
	</tr>
		<#assign ttotalUnit = 0>
		<#assign ptotalUnit = 0>
		<#assign packagetotalUnit = 0>
		<#assign vouchertotalUnit = 0>
		<#assign tsumAmount = 0>
		<#assign psumAmount = 0>
		<#assign packagesumAmount = 0>
		<#assign vouchersumAmount = 0>
	<#if (summaryList?size>0) >
	<#list summaryList as summaryObject>
		<#if summaryObject.prodType == "CA-TREATMENT">
    		<#assign ttotalUnit += summaryObject.unit>
			<#assign tsumAmount += summaryObject.amount>
		</#if>
		<#if summaryObject.prodType == "CA-GOODS">
			<#assign ptotalUnit += summaryObject.unit>
			<#assign psumAmount += summaryObject.amount>
		</#if>
		<#if summaryObject.prodType == "PREPAID">
			<#if summaryObject.categoryName == "Cash Voucher" || summaryObject.categoryName == "Treatment Voucher">
				<#assign packagetotalUnit += summaryObject.unit>
				<#assign packagesumAmount += summaryObject.amount>
			</#if>
			<#if summaryObject.categoryName == "Cash Voucher" || summaryObject.categoryName == "Treatment Voucher">
				<#assign vouchertotalUnit += summaryObject.unit>
				<#assign vouchersumAmount += summaryObject.amount>
			</#if>		
		</#if>
	</#list>
	</#if>
	<tr>
		<td height="25">Number of Treatments Sold:-unit:&nbsp;${ttotalUnit};&nbsp;Total:&nbsp;${tsumAmount}</td>
	</tr>
	<#if (summaryList?size>0) >
	<#list summaryList as summaryObject>
		<#if summaryObject.prodType == "CA-TREATMENT">
			<tr>
				<td style="word-wrap:break-word;word-break:break-all;">
					-&nbsp;&nbsp;${summaryObject.categoryName }&nbsp;
		            <br>-&nbsp;&nbsp;unit:${summaryObject.unit};&nbsp;total:${summaryObject.amount}
				</td>
			</tr>
		</#if>
	</#list>
	</#if>
	<tr>
		<td height="25">Number of Product Sold:-unit:&nbsp;${ptotalUnit};&nbsp;Total:&nbsp;${psumAmount}</td>
	</tr>
	<tr>
		<td height="25">Number of Package Sold:-unit:&nbsp;${packagetotalUnit};&nbsp;Total:&nbsp;${packagesumAmount}</td>
	</tr>
	<tr>
		<td height="25">Number of Voucher Sold:-unit:&nbsp;${vouchertotalUnit};&nbsp;Total:&nbsp;${vouchersumAmount}</td>
	</tr>
</tbody></table>
</div>
<br>
<div class="table-responsive">
<table class="table report_inner_table table-striped" width="100%" role="presentation">
	<tbody><tr>
		<th style="color: #ffffff; background: #808080;font-size: 16px!important;text-align: left;padding:7px 10px;">C) Therapist Breakdown</th>
	</tr>
	<tr><td>
    <div class="Daily_Total_Sales_div" style="width: 49.5%;
    margin: 0 auto;
    display: inline-block;
    vertical-align: top;
    word-wrap: break-word;
    table-layout: fixed;"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="table report_inner_table table-striped" role="presentation">
    <tbody>
	<#if staffSummary?exists>
		<#list staffSummary?keys as key>
		<tr>
			<td height="25">
				<h5 style="width: 100%;background: none;color: #404040;margin: 0;padding: 0;font-weight: 600">-&nbsp;&nbsp;${key}</h5>
			</td>
		</tr>
		<#assign ttotalUnit_staff = 0>
		<#assign ptotalUnit_staff = 0>
		<#assign packagetotalUnit_staff = 0>
		<#assign vouchertotalUnit_staff = 0>
		<#assign tsumAmount_staff = 0>
		<#assign psumAmount_staff = 0>
		<#assign packagesumAmount_staff = 0>
		<#assign vouchersumAmount_staff = 0>
		<#assign vos=staffSummary[key] >
		<#list vos as vo>
			<#if vo.prodType == "CA-TREATMENT">
        		<#assign ttotalUnit_staff += vo.unit>
				<#assign tsumAmount_staff += vo.amount>
			</#if>
			<#if vo.prodType == "CA-GOODS">
				<#assign ptotalUnit_staff += vo.unit>
				<#assign psumAmount_staff += vo.amount>
			</#if>
			<#if vo.prodType == "PREPAID">
				<#if vo.categoryName == "Package">
					<#assign packagetotalUnit_staff += vo.unit>
					<#assign packagesumAmount_staff += vo.amount>
				</#if>
				<#if vo.categoryName == "Voucher">
					<#assign vouchertotalUnit_staff += vo.unit>
					<#assign vouchersumAmount_staff += vo.amount>
				</#if>		
			</#if>
        </#list>
		<tr>
			<td height="25">Treatment:&nbsp;Number of Treatment Sold:${ttotalUnit_staff};&nbsp;total:${tsumAmount_staff}</td>
		</tr>
		<tr>
			<td height="25">Product:&nbsp;Number of Product Sold:${ptotalUnit_staff};&nbsp;total:${psumAmount_staff}</td>
		</tr>
		<tr>
			<td height="25">Package:&nbsp;Number of Package Sold:${packagetotalUnit_staff};&nbsp;total:${packagesumAmount_staff}</td>
		</tr>
		<tr>
			<td height="25">Voucher:&nbsp;Number of Voucher Sold:${vouchertotalUnit_staff};&nbsp;:${vouchersumAmount_staff}</td>
		</tr>
	</#list>
	</#if>
    </tbody></table>
    </div>
    </td>
    </tr>
</tbody></table>
</div>
</div></td>
                  </tr>
               </table>
						</div>
					</td>
				</tr>
				<tr>
					<td style="line-height:0">&nbsp;</td>

</tbody></table>
</div>
</div>
    </td>
       </tr>
				</tbody>
			</table>
<!--content-->
', '', null, 1, now(), 'admin', now(), 'admin');


/* create send booking email by william -- 2018-8-10 */

INSERT INTO loccitane.mkt_email_template (type, subject, content, document_id, company_id, is_active, created, created_by, last_updated, last_updated_by) VALUES ('SEND_BOOKING_NOTIFICATION_EMAIL', 'Send Booking Notification Email', '<!--content-->
             <table width="800" cellspacing="0" cellpadding="0" border="0" align="center">
                   <tbody>
                   <tr>
                       <td bgcolor="#ffffff">
                           <div style="box-shadow:0 0 15px rgba(0,0,0,0.1);padding: 20px 20px 20px 20px">
                               <TABLE width="100%" cellspacing="1" cellpadding="1" border="0">
                                   <TR>
                                       <TD colspan="2" style="font-size: 14px;">Dear ${user.firstName},
                                       </TD>
                                   </TR>
                                   <TR>
                                       <TD colspan="2"> </TD>
                                   </TR>
                                   <TR>
                                       <TD colspan="2">
                                         Thank you for your booking at Sense of Touch.
                                       </TD>
                                   </TR>
									<TR>
                                       <TD colspan="2">
                                         Please find your appointment details below:
                                       </TD>
                                   </TR>
									<TR>
                                       <TD >
                                         Appointment Date:${book.appointmentDateFormat}
                                       </TD>
                                       <TD >
											Location:${book.shop.name}
										</TD>
									</TR>
									<TR>
									<TD colspan="2">
									 <TABLE width="100%" cellspacing="1" cellpadding="1" border="0">
										<#list book.bookItems as bi><tr>
                                       		<td>Appointment Time: ${bi.appointmentTimeFormat}~${bi.appointmentEndTimeFormat}</td>
                                       		<td>Treatment: ${bi.productName}</td>
                                       		<td>Duration: ${bi.duration}</td>
                                       		<td>Therapist: ${bi.therapists}</td></tr>
										</#list>
 									</TABLE>
 									</TD>
                                   </TR>
                                   <TR>
                                       <TD colspan="2">If you would like to cancel or amend an appointment, please inform us a minimum of 24-hours prior to your appointment.</TD>
</TR><TR>
                                       <TD colspan="2">We look forward to welcoming you soon at Sense of Touch.</TD>
</TR><TR>
                                       <TD colspan="2">Warm Regards,</TD>
</TR><TR>
                                       <TD colspan="2">Sense of Touch Team</TD>
</TR>
                               </TABLE>
                           </div>
                       </td>
                   </tr>
                   <tr>
                       <td style="line-height:0"> </td>
                   </tr>
 </tbody></table>', '', null, 1, '2018-01-08 10:19:05', 'admin', '2018-01-08 10:19:14', 'admin');


/* create by william -- 2018-9-10 */
INSERT INTO loccitane.mkt_email_template (type, subject, content, document_id, company_id, is_active, created, created_by, last_updated, last_updated_by) VALUES ('SEND_BOOKING_REMINDER_NOTIFICATION_EMAIL', '24 Hours Booking Reminder', '<!--content-->
             <table width="800" cellspacing="0" cellpadding="0" border="0" align="center">
                   <tbody>
                   <tr>
                       <td bgcolor="#ffffff">
                           <div style="box-shadow:0 0 15px rgba(0,0,0,0.1);padding: 20px 20px 20px 20px">
                               <TABLE width="100%" cellspacing="1" cellpadding="1" border="0">
                                   <TR>
                                       <TD colspan="2" style="font-size: 14px;">Dear ${user.firstName},
                                       </TD>
                                   </TR>
                                   <TR>
                                       <TD colspan="2"> </TD>
                                   </TR>
                                   <TR>
                                       <TD colspan="2">
                                         Thank you for your booking at Sense of Touch.
                                       </TD>
                                   </TR>
									<TR>
                                       <TD colspan="2">
                                         Please find your appointment details below:
                                       </TD>
                                   </TR>
									<#list books as b><TR>
                                       <TD >
                                       		<strong>Appointment Date:${b.appointmentDateFormat}</strong>
                                       </TD>
                                       <TD >
											<strong>Location:${b.bookShopName}</strong>
										</TD>
									</TR>
									<TR>
									<TD colspan="2">
									 <#list b.bookItemVOs as bi><TABLE width="100%" cellspacing="1" cellpadding="1" border="1">
										<tr>
                                       		<td>Appointment Time: ${bi.appointmentTimeFormat}~${bi.appointmentEndTimeFormat}</td>
											<td>Treatment: ${bi.productName}</td>
                                       		<td>Duration: ${bi.duration}</td>
                                       		<td>Therapist: ${bi.therapists}</td>
                                       	</tr>
 									</TABLE></#list>
 									</TD>
                                   </TR></#list>
                                   <TR>
                                       <TD colspan="2">If you would like to cancel or amend an appointment, please inform us a minimum of 24-hours prior to your appointment.</TD>
</TR><TR>
                                       <TD colspan="2">We look forward to welcoming you soon at Sense of Touch.</TD>
</TR><TR>
                                       <TD colspan="2">Warm Regards,</TD>
</TR><TR>
                                       <TD colspan="2">Sense of Touch Team</TD>
</TR>
                               </TABLE>
                           </div>
                       </td>
                   </tr>
                   <tr>
                       <td style="line-height:0"> </td>
                   </tr>
 </tbody></table>', '', null, 1, now(), 'admin', now(), 'admin');

