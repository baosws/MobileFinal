const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp();

// Take the text parameter passed to this HTTP endpoint and insert it into the
// Realtime Database under the path /messages/:pushId/original
// exports.addMessage = functions.https.onRequest((req, res) => {
//   // Grab the text parameter.
//   const original = req.query.text;
//   // Push the new message into the Realtime Database using the Firebase Admin SDK.
//   return admin.database().ref('/messages').push({original: original}).then((snapshot) => {
//     // Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
//     return res.redirect(303, snapshot.ref.toString());
//   });
// });

exports.addShop = functions.https.onCall((data, context) => {
    const uid = data.uid;
    const name = data.name;
    const address = data.address;
    const lat = data.lat;
    const lng = data.lng;
    const open_hour = data.open_hour;
    const close_hour = data.close_hour;
    const sid = (new Date()).getTime().toString();
    console.log('uid ' + uid);
    console.log('name ' + name);
    console.log('address ' + address);
    console.log('loc ' + lat + ' ' + lng);
    console.log('open_hour ' + open_hour);
    console.log('close_hour ' + close_hour);
    console.log('sid ' + sid);
    
    return admin.database().ref('/shops').push({uid: uid, name: name, address: address, loc: {lat: lat, lng: lng}, open_hour: open_hour, close_hour: close_hour, sid: sid}).then(() => {
        return {sid: sid};
    });
});

exports.addItem = functions.https.onCall((data, context) => {
    const sid = data.sid;
    const name = data.name;
    const description = data.description;
    const category = data.category;
    const price = data.price;
    const quantity = data.quantity;
    const color = data.color;
    const size = data.size;
    const iid = (new Date()).getTime().toString();
    
    console.log('iid ' + iid);
    console.log('sid ' + sid);
    console.log('name ' + name);
    console.log('category ' + category);
    console.log('description ' + description);
    console.log('price ' + price);
    console.log('quantity ' + quantity);
    console.log('color ' + color);
    console.log('size ' + size);

    return admin.database().ref('/items').child(iid).set({iid: iid, sid: sid, name: name, description: description, category: category, price: price, quantity: quantity, variation: {color: color, size: size}}).then(() => {
        return {iid: iid};
    });
});

exports.addCart = functions.https.onCall((data, context) => {
    const uid = data.uid;
    const quantity = data.quantity;
    let variation = data.variation;
    if (variation === null) {
        variation = 'none';
    }
    const iid = data.iid;

    console.log('addCart: uid = %s, iid = %s, variation = %s, quantity = %s', uid, iid, variation, quantity);

    return admin.database().ref('/carts').child(uid).child(iid).child(variation).child('quantity').set(quantity).then(() => {
        return true;
    }).catch((error) => {
        return false;
    });
});

exports.addOrder = functions.https.onCall((data, context) => {
    const uid = data.uid;
    const items = data.items;
    const payment_method = data.payment_method;
    const address = data.address;
    const oid = (new Date()).getTime().toString();

    console.log('uid ' + uid);
    console.log('items ' + items);
    console.log('payment_method ' + payment_method);
    console.log('address ' + address);

    return admin.database().ref('/orders').push({oid: oid, uid: uid, items: items, payment_method: payment_method, address: address}).then(() => {
        return {oid: oid};
    });
});
