from flask import Flask, jsonify, request

# Flask 클래스를 객체화 (instantiate)시켜서 app 에 저장
# 이 변수 app이 API어플리케이션이 된다
app = Flask(__name__)

app.users = {}
app.id_count = 1


# ping 테스트
@app.route("/ping", methods=['GET'])
def ping() :
    return "pong"


@app.route("/sign-up", methods=['POST'])
def sign_up() :
	new_user				= request.json
	new_user['id']	 		= app.id_count
	app.users[app.id_count]	= new_user
	app.id_count			= app.id_count + 1

	return jsonify(new_user)

