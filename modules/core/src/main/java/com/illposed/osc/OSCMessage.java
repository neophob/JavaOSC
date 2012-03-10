/*
 * Copyright (C) 2003-2006, C. Ramakrishnan / Illposed Software.
 * All rights reserved.
 *
 * This code is licensed under the BSD 3-Clause license.
 * See file LICENSE (or LICENSE.html) for more information.
 */

package com.illposed.osc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.illposed.osc.utility.OSCJavaToByteArrayConverter;

/**
 * An simple (non-bundle) OSC message.
 *
 * An OSC message is made up of an address (the receiver of the message)
 * and arguments (the content of the message).
 *
 * @author Chandrasekhar Ramakrishnan
 */
public class OSCMessage extends OSCPacket {

	private String address;
	private List<Object> arguments;

	/**
	 * Create an empty OSC Message.
	 * In order to send this OSC message, you need to set the address
	 * and, perhaps, some arguments.
	 */
	public OSCMessage() {
		this(null);
	}

	/**
	 * Create an OSCMessage with an address already initialized.
	 * @param address the recipient of this OSC message
	 */
	public OSCMessage(String address) {
		this(address, null);
	}

	/**
	 * Create an OSCMessage with an address and arguments already initialized.
	 * @param address    the recipient of this OSC message
	 * @param arguments  the data sent to the receiver
	 */
	public OSCMessage(String address, Collection<Object> arguments) {

		this.address = address;
		if (arguments == null) {
			this.arguments = new LinkedList();
		} else {
			this.arguments = new ArrayList(arguments);
		}
		init();
	}

	/**
	 * The receiver of this message.
	 * @return the receiver of this OSC Message
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set the address of this message.
	 * @param address the receiver of the message
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Add an argument to the list of arguments.
	 * @param argument a Float, String, Integer, BigInteger, Boolean or
	 *          an array of these
	 */
	public void addArgument(Object argument) {
		arguments.add(argument);
	}

	/**
	 * The arguments of this message.
	 * @return the arguments to this message
	 */
	public List<Object> getArguments() {
		return Collections.unmodifiableList(arguments);
	}

	/**
	 * Convert the address into a byte array. Used internally.
	 * @param stream OscPacketByteArrayConverter
	 */
	protected void computeAddressByteArray(OSCJavaToByteArrayConverter stream) {
		stream.write(address);
	}

	/**
	 * Convert the arguments into a byte array. Used internally.
	 * @param stream OscPacketByteArrayConverter
	 */
	protected void computeArgumentsByteArray(OSCJavaToByteArrayConverter stream)
	{
		stream.write(',');
		if (null == arguments) {
			return;
		}
		stream.writeTypes(arguments);
		for (Object argument : arguments) {
			stream.write(argument);
		}
	}

	/**
	 * Convert the message into a byte array. Used internally.
	 * @param stream OscPacketByteArrayConverter
	 */
	protected void computeByteArray(OSCJavaToByteArrayConverter stream) {
		computeAddressByteArray(stream);
		computeArgumentsByteArray(stream);
		byteArray = stream.toByteArray();
	}
}
