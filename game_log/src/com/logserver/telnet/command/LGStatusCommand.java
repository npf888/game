package com.logserver.telnet.command;

import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.mina.common.IoSession;

import com.common.model.ServerStatus;
import com.logserver.common.Globals;


/**
 * 
 * @author Thinker
 *
 */
public class LGStatusCommand extends LoginedTelnetCommand 
{

	public LGStatusCommand()
	{
		super("LOG_STATUS");
	}

	@Override
	protected void doExec(String command, Map<String, String> params,IoSession session)
	{
		JSONArray _arrays = new JSONArray();
		 
			
		ServerStatus _status = Globals.getLogServerStatus();
		_status.refresh();
		if (_status != null)
		{
			_arrays.add(_status.getStatusJSON());
		}
		this.sendMessage(session, _arrays.toString());
	}
}
